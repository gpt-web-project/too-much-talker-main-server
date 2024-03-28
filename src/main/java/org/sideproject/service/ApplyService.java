package org.sideproject.service;

import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.EachUserJobApplyQuestion;
import org.sideproject.model.dto.request.JobApplicationRequest;
import org.sideproject.model.dto.response.JobAnswerResponse;
import org.sideproject.model.entity.JobApplicationInfo;
import org.sideproject.model.entity.JobApplicationQuestion;
import org.sideproject.repository.JobApplicationInfoRepository;
import org.sideproject.repository.JobApplicationQuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class ApplyService {
    private final JobApplicationInfoRepository jobApplicationInfoRepository; //지원 기업정보
    private final JobApplicationQuestionRepository jobApplicationQuestionRepository; // 지원 기업에 대한 자기소개서 질문
    private final UserService userService;

    public void saveJobInfo(JobApplicationRequest jobInfoApplyRequest){

        JobApplicationInfo jobApplicationInfo = new JobApplicationInfo(
                userService.getUserById(jobInfoApplyRequest.getUserId()),
                jobInfoApplyRequest.getCompanyName(),
                jobInfoApplyRequest.getJobTitle(),
                jobInfoApplyRequest.getJobDescription(),
                jobInfoApplyRequest.getQualificationRequirements()
        );


        // questionList 에서 JobApplicationQuestion 엔티티 생성 및 연결
        for (String questionText : jobInfoApplyRequest.getQuestionList()) {
            JobApplicationQuestion question = new JobApplicationQuestion(questionText);
            jobApplicationInfo.addQuestion(question);
        }

        jobApplicationInfoRepository.save(jobApplicationInfo);
        trigger(jobInfoApplyRequest.getUserId());
    }

    private void trigger(Long userId) {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = String.format("{\"id\": %d}", userId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:5000/user-get-answer"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // 비동기 요청 보내기
        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // 비동기 요청이 완료될 때 수행될 작업 지정
        responseFuture.thenApply(HttpResponse::body)
                .thenAccept(System.out::println) // 응답 본문 출력
                .exceptionally(e -> { // 예외 처리
                    e.printStackTrace();
                    return null;
                });
    }

    public List<JobAnswerResponse> getAnswer(Long userId){
        List<JobAnswerResponse> responses = new ArrayList<>();

        // 모든 JobApplicationInfo 객체 조회
        List<JobApplicationInfo> applicationInfos = jobApplicationInfoRepository.findAllByUserId(userId);

        if (applicationInfos.isEmpty()) {
            throw new RuntimeException("자기소개 작성 요청을 해주세요.");
        }

        for (JobApplicationInfo applicationInfo : applicationInfos) {
            List<EachUserJobApplyQuestion> answerList = new ArrayList<>();
            for (JobApplicationQuestion each : applicationInfo.getQuestions()) {
                // 답변이 있는지 확인, 없으면 "AI가 답변 작성중입니다."
                String answer = each.getAnswer() != null ? each.getAnswer() : "AI가 답변 작성중입니다.";
                answerList.add(new EachUserJobApplyQuestion(each.getQuestion(), answer));
            }
            // 각 JobApplicationInfo 객체에 대한 응답을 리스트에 추가
            responses.add(new JobAnswerResponse(answerList, applicationInfo.getCompanyName(), applicationInfo.getJobTitle()));
        }

        return responses;
    }


}
