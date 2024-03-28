package org.sideproject.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "experience_details")
public class ExperienceDetail {
    // 지원자 이력사항 상세 테이블

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id")
    private ExperienceHistory experienceHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String skillHighlight; // 강조하고싶은 역량

    public ExperienceDetail(ExperienceHistory experienceHistory, Resume resume, String description) {
        this.experienceHistory = experienceHistory;
        this.resume = resume;
        this.description = description;
    }

    public ExperienceDetail(String description){
        this.description = description;
    }
}
