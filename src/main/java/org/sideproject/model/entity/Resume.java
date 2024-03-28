package org.sideproject.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "resume")
public class Resume  extends Auditable{
    // 이력서

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "resume", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceHistory> experienceHistoryList = new ArrayList<>();

    public Resume(User user){
        this.user = user;
    }


    public Resume(User user,List<ExperienceHistory> experienceHistoryList){
        this.user = user;
        this.experienceHistoryList = experienceHistoryList;
    }

}
