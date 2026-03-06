package com.demo.repository;

import com.demo.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findAllByCourseId(Long courseId);

    @Query("""
            SELECT e FROM Exam e
            JOIN e.course c
            JOIN c.students s
            WHERE s.id = :studentId
            """)
    List<Exam> findAllByStudentId(@Param("studentId") Long studentId);
}