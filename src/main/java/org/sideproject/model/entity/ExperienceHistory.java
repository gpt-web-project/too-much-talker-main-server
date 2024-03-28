package org.sideproject.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sideproject.model.metadata.WorkCode;

import javax.persistence.*;
import java.security.PublicKey;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@Table(name = "experience_history")
    public class ExperienceHistory {
    // 지원자 이력사항
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @OneToOne(mappedBy = "experienceHistory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ExperienceDetail experienceDetail;

    @Column(nullable = false)
    private String institutionName;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private WorkCode type;

    public ExperienceHistory(Resume resume, String institutionName, String position, LocalDate startDate, LocalDate endDate, WorkCode type) {
        this.resume = resume;
        this.institutionName = institutionName;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public ExperienceHistory(ExperienceDetail experienceDetail){
        this.experienceDetail = experienceDetail;
    }

}
