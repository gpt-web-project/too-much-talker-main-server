package org.sideproject.model.metadata;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {
    OK("저장 성공"),
    ERROR("에러 발생"),
    ;
    private final String msg;
}
