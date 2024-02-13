package org.sideproject.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.sideproject.model.entity.ExperienceHistory;
import org.sideproject.model.metadata.WorkCode;

import javax.validation.constraints.Null;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class EachResume {

    private Long resumeId; // Resume 엔티티의 ID를 참조

    private Long eachHistoryId; // 각 경험의 ID

    private String institutionName;
    private String position;


    private String historyDetail; // 각 이력사항 상세 항목 추가(경험 서술)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Seoul")
    private LocalDate startDate;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private WorkCode type; // WorkCode는 열거형 또는 별도의 클래스일 수 있음

    public EachResume(ExperienceHistory eachHistory){
        this.resumeId = eachHistory.getResume().getId();
        this.eachHistoryId = eachHistory.getId();
        this.institutionName = eachHistory.getInstitutionName();
        this.position = eachHistory.getPosition();

        if(eachHistory.getExperienceDetail() != null){
            this.historyDetail = eachHistory.getExperienceDetail().getDescription();
        }

        this.startDate = eachHistory.getStartDate();

        if(eachHistory.getEndDate() != null){
            this.endDate = eachHistory.getEndDate();
        }

        this.type = eachHistory.getType();
    }
}
