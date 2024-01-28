package org.sideproject.model.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkCode {
    // 경험 유형 코드
    EDUCATION("학력사항", "EDUCATION"),
    EXPERIENCE("경력사항", "EXPERIENCE"),
    LICENSES("자격증", "LICENSES"),
    ACTIVITY("학내외활동사항", "ACTIVITY"),
    ETC("기타", "ETC");
    ;

    private final String workName;
    private final String workCode;
}
