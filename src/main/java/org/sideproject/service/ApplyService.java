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

    public JobAnswerResponse getAnswer(Long userId){
        // 페이지 객체 직접 처리
        // Todo. 리스트로 내려올때 처리
        Page<JobApplicationInfo> applicationInfoPage = jobApplicationInfoRepository.findLatestByUserId(userId, PageRequest.of(0, 1));

        // 페이지 내용이 비어 있는지 확인
        if (!applicationInfoPage.hasContent()) {
            throw new RuntimeException("자기소개 작성 요청을 해주세요.");
        }

        // 페이지에서 첫 번째 JobApplicationInfo 객체를 가져옴
        JobApplicationInfo applicationInfo = applicationInfoPage.getContent().get(0);

        // 첫 번째 질문의 답변이 없는 경우 처리
        if (applicationInfo.getQuestions().isEmpty() || applicationInfo.getQuestions().get(0).getAnswer() == null) {
            trigger(userId);
            throw new RuntimeException("AI가 답변 작성중입니다.");
        } else {
            List<EachUserJobApplyQuestion> answerList = new ArrayList<>();
            // 모든 질문과 답변을 처리
            for (JobApplicationQuestion each : applicationInfo.getQuestions()) {
                answerList.add(new EachUserJobApplyQuestion(each.getQuestion(), each.getAnswer()));
            }
            // 응답 객체 생성 및 반환
            return new JobAnswerResponse(answerList, applicationInfo.getCompanyName(), applicationInfo.getJobTitle());
        }
    }
}
