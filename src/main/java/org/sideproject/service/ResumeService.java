package org.sideproject.service;

import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.EachResume;
import org.sideproject.model.dto.request.ResumeRequest;
import org.sideproject.model.dto.response.ResumeResponse;
import org.sideproject.model.entity.ExperienceDetail;
import org.sideproject.model.entity.ExperienceHistory;
import org.sideproject.model.entity.Resume;
import org.sideproject.model.entity.User;
import org.sideproject.repository.ExperienceDetailRepository;
import org.sideproject.repository.ExperienceHistoryRepository;
import org.sideproject.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ExperienceDetailRepository experienceDetailRepository;
    private final ExperienceHistoryRepository experienceHistoryRepository;
    private final UserService userService;

    @Transactional
    public void saveUserResume(ResumeRequest resumeRequest) {

        User user = userService.getUserById(resumeRequest.getUserId());

        Optional<List<Resume>> resumes = resumeRepository.findAllByUser_Id(resumeRequest.getUserId());

        if (resumes.isPresent() && !resumes.get().isEmpty()) {
            // 존재한다면 해당 레코드를 모두 삭제합니다.
            resumeRepository.deleteByUserId(resumeRequest.getUserId());
        }

        Resume savedResume = resumeRepository.save(new Resume(user));

        List<ExperienceHistory> savedHistories = new ArrayList<>();
        for (EachResume each : resumeRequest.getUserResumeList()) {
            ExperienceHistory history = new ExperienceHistory(savedResume, each.getInstitutionName(), each.getPosition(), each.getStartDate(), each.getEndDate(), each.getType());
            ExperienceHistory savedHistory = experienceHistoryRepository.save(history);

            // historyDetail이 null이 아닌 경우에만 ExperienceDetail을 생성하고 저장합니다.
            if (each.getHistoryDetail() != null && !each.getHistoryDetail().isEmpty()) {
                ExperienceDetail detail = new ExperienceDetail(savedHistory, savedResume, each.getHistoryDetail());
                // ExperienceHistory와 ExperienceDetail 간의 양방향 관계 설정
                savedHistory.setExperienceDetail(detail);
                experienceDetailRepository.save(detail);
            }

            savedHistories.add(savedHistory);
        }
    }

    public ResumeResponse getUserResume(long userId){
        Resume key = resumeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId).get();

        List<ExperienceHistory> experienceHistoryList = experienceHistoryRepository.findAllByResume_Id(key.getId());
        List<EachResume> returnList = new ArrayList<>();
        for(ExperienceHistory each : experienceHistoryList){
            returnList.add(new EachResume(each));
        }

        return new ResumeResponse(returnList);
    }

}
