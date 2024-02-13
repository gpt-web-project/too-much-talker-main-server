package org.sideproject.service;

import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.request.JobApplicationRequest;
import org.sideproject.model.dto.response.JobAnswerResponse;
import org.sideproject.model.entity.JobApplicationInfo;
import org.sideproject.model.entity.JobApplicationQuestion;
import org.sideproject.repository.JobApplicationInfoRepository;
import org.sideproject.repository.JobApplicationQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    }

    private void trigger(){
        // 외부 서버로 요청
    }

    public JobAnswerResponse getAnswer(Long userId){
        Optional<JobApplicationInfo> applicationInfo = jobApplicationInfoRepository.findAllByUser_Id(userId);

        if(applicationInfo.isEmpty()){
            throw new RuntimeException("자기소개 작성 요청을 해주세요.");
        }else if(applicationInfo.get().getQuestions().get(1).getAnswer() != null){
            throw new RuntimeException("AI가 답변 작성중입니다.");
        }

        return null;
    }
}
