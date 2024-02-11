package org.sideproject.repository;

import org.sideproject.model.entity.ExperienceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceHistoryRepository extends JpaRepository<ExperienceHistory, Long> {
}
