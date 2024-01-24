package org.sideproject.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "user_job_application_info")
public class JobApplicationInfo  extends Auditable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String companyName;

    @JoinColumn(name = "USER_ID")
    @Column(nullable = false)
    private String jobTitle;
    private String jobDescription;
    private String qualificationRequirements;
    private String jobPostingUrl;
}
