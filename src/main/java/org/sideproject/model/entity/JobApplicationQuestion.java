package org.sideproject.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@Table(name = "job_application_questions")
public class JobApplicationQuestion extends Auditable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private JobApplicationInfo jobApplicationInfo;

    @Column(nullable = false)
    private String question;
    private String answer;

    public JobApplicationQuestion(String question){
        this.question = question;
    }
}
