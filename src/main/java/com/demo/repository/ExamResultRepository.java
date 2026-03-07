package com.demo.repository;

import com.demo.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    // Fetch a specific result (unique constraint: student + exam)
    Optional<ExamResult> findByStudentIdAndExamId(Long studentId, Long examId);

    // Guard against duplicate submissions
    boolean existsByStudentIdAndExamId(Long studentId, Long examId);

    // All results for a student
    List<ExamResult> findAllByStudentId(Long studentId);

    // All results for a specific exam
    List<ExamResult> findAllByExamId(Long examId);

    // All results for a student in a specific course
    @Query("""
            SELECT er FROM ExamResult er
            JOIN er.exam e
            WHERE er.student.id = :studentId
            AND e.course.id = :courseId
            """)
    List<ExamResult> findAllByStudentIdAndCourseId(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId
    );

    // Average marks scored by a student across all exams in a course
    @Query("""
            SELECT AVG(er.marksObtained) FROM ExamResult er
            JOIN er.exam e
            WHERE er.student.id = :studentId
            AND e.course.id = :courseId
            """)
    Double findAverageMarksByStudentIdAndCourseId(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId
    );

    // Average marks for all students in a specific exam (leaderboard / analytics)
    @Query("""
            SELECT AVG(er.marksObtained) FROM ExamResult er
            WHERE er.exam.id = :examId
            """)
    Double findAverageMarksByExamId(@Param("examId") Long examId);

    // Top scorers in an exam
    @Query("""
            SELECT er FROM ExamResult er
            JOIN FETCH er.student
            WHERE er.exam.id = :examId
            ORDER BY er.marksObtained DESC
            """)
    List<ExamResult> findTopScorersByExamId(@Param("examId") Long examId);
}
