package org.sideproject.repository;

import org.sideproject.model.entity.JobApplicationInfo;
import org.sideproject.model.entity.JobApplicationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplicationInfoRepository extends JpaRepository<JobApplicationInfo, Long> {
    Optional<JobApplicationInfo> findAllByUser_Id(Long userId);
}
