package org.sideproject.repository;

import org.sideproject.model.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("SELECT r FROM Resume r WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    Optional<Resume> findTopByUser_IdOrderByCreatedAtDesc(@Param("userId") long userId);

    Optional<Resume> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<List<Resume>> findAllByUser_Id(@Param("userId") long userId);

    @Transactional
    void deleteByUserId(Long userId);
}
