package org.sideproject.repository;

import org.sideproject.model.entity.ExperienceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceHistoryRepository extends JpaRepository<ExperienceHistory, Long>{
    List<ExperienceHistory> findAllByResume_Id(long resumeId);

}
