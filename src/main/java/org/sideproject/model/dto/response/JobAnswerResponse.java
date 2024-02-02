package org.sideproject.model.dto.response;

import lombok.Getter;
import org.sideproject.model.dto.EachUserJobApplyQuestion;

import java.util.List;

@Getter
public class JobAnswerResponse {
    List<EachUserJobApplyQuestion> answerList;

    public JobAnswerResponse(List<EachUserJobApplyQuestion> answerList){
        this.answerList = answerList;
    }
}
