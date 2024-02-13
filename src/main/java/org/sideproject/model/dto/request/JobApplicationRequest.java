package org.sideproject.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobApplicationRequest {
    @NotEmpty
    long userId;

    @NotEmpty
    String companyName;

    @NotEmpty
    String jobTitle;
    String jobDescription;
    String qualificationRequirements;

    @NotNull
    List<String> questionList;
}
