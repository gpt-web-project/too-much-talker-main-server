package org.sideproject.repository;

import org.sideproject.model.entity.JobApplicationQuestion;
import org.sideproject.model.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationQuestionRepository  extends JpaRepository<JobApplicationQuestion, Long> {
}
