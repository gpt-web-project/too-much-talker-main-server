package org.sideproject.repository;

import org.sideproject.model.entity.JobApplicationInfo;
import org.sideproject.model.entity.JobApplicationQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobApplicationInfoRepository extends JpaRepository<JobApplicationInfo, Long> {
    Optional<List<JobApplicationInfo>> findAllByUser_Id(Long userId);


    @Query("SELECT r FROM JobApplicationInfo r WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    Page<JobApplicationInfo> findLatestByUserId(@Param("userId") Long userId, Pageable pageable);
}
