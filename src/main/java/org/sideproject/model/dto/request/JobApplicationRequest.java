package org.sideproject.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobApplicationRequest {
    long userId;
    String companyName;
    String jobTitle;
    String jobDescription;
    String qualificationRequirements;
    List<String> questionList;
}
