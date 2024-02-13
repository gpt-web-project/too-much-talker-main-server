package org.sideproject.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;
import org.sideproject.model.dto.EachUserJobApplyQuestion;

import java.util.List;

@Getter
@Setter
public class JobAnswerResponse {
    private String companyName; // 회사명
    private String jobTitle; // 지원 직무
    private List<EachUserJobApplyQuestion> answerList;

    public JobAnswerResponse(List<EachUserJobApplyQuestion> answerList,String companyName,String jobTitle){
        this.answerList = answerList;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
    }
}
