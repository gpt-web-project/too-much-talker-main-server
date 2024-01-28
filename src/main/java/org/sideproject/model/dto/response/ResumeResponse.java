package org.sideproject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sideproject.model.dto.EachResume;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResumeResponse {
    List<EachResume> userResumeList;
}