package com.demo.repository;

import com.demo.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    List<ExamResult> findAllByStudentId(Long studentId);

    List<ExamResult> findAllByExamId(Long examId);

    boolean existsByStudentIdAndExamId(Long studentId, Long examId);
}