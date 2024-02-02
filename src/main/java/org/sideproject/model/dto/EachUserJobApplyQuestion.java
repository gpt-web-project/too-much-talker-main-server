package org.sideproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EachUserJobApplyQuestion {
    // 유저 지원 회사 자기소개 질문
    private String question;
    private String answer;
}
