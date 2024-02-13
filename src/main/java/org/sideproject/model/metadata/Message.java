package org.sideproject.model.metadata;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {
    OK("저장 성공"),
    ;
    private final String msg;
}
