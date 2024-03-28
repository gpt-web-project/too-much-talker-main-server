package org.sideproject.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "jobApplicationInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplicationQuestion> questions = new ArrayList<>();

    @Column(nullable = false)
    private String companyName;

    @JoinColumn(name = "USER_ID")
    @Column(nullable = false)
    private String jobTitle;
    private String jobDescription;
    private String qualificationRequirements;
    private String jobPostingUrl;

    // 생성자
    public JobApplicationInfo(User user, String companyName, String jobTitle, String jobDescription, String qualificationRequirements, String jobPostingUrl) {
        this.user = user;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.qualificationRequirements = qualificationRequirements;
        this.jobPostingUrl = jobPostingUrl;
    }

    public JobApplicationInfo(User user, String companyName, String jobTitle, String jobDescription, String qualificationRequirements) {
        this.user = user;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.qualificationRequirements = qualificationRequirements;
    }

    // questions 필드에 대한 추가 및 제거 메서드
    public void addQuestion(JobApplicationQuestion question) {
        questions.add(question);
        question.setJobApplicationInfo(this);
    }

    public void removeQuestion(JobApplicationQuestion question) {
        questions.remove(question);
        question.setJobApplicationInfo(null);
    }
}
