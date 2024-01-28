package org.sideproject.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sideproject.model.dto.EachResume;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResumeRequest {
    long userId;
    List<EachResume> userResumeList;
}
