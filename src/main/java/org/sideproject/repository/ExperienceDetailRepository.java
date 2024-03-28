package org.sideproject.repository;

import org.sideproject.model.entity.ExperienceDetail;
import org.sideproject.model.entity.ExperienceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceDetailRepository  extends JpaRepository<ExperienceDetail, Long> {
}
