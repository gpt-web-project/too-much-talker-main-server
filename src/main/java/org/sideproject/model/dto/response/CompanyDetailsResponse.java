package org.sideproject.model.dto.response;

import lombok.Getter;

@Getter
public class CompanyDetailsResponse {
    // 캡쳐로 업로드시 기업정보 자동으로 Return
    String companyName;
    String jobTitle;
    String jobDescription;
    String qualificationRequirements;

    public CompanyDetailsResponse(String companyName, String jobTitle, String jobDescription, String qualificationRequirements) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.qualificationRequirements = qualificationRequirements;
    }
}
