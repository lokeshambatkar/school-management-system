package com.demo.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO returned to the client after any Exam operation.
 *
 * DESIGN DECISIONS:
 *
 * 1. We include 'courseId' and 'courseTitle' — not the full CourseResponse.
 *    WHY? The client needs to know WHICH course this exam belongs to,
 *    but they don't need the full Course object (that would be over-fetching).
 *    Two flat fields (id + title) give enough context without nesting.
 *    This pattern is called "flattening" — preferred over deep nesting in REST APIs.
 *
 * 2. We do NOT include the list of ExamResults here.
 *    WHY? An exam can have hundreds of results.
 *    Results are fetched via /exam-results/exam/{examId}.
 *
 * 3. We include 'resultCount' — how many students have submitted results for this exam.
 *    Useful for teacher dashboard: "15/30 students have submitted."
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResponse {

    private Long id;
    private String title;
    private LocalDate examDate;
    private Integer totalMarks;

    /**
     * Flattened course info — avoids deep nesting.
     * Client gets what they need without the full Course object.
     */
    private Long courseId;
    private String courseTitle;

    /**
     * How many results have been recorded for this exam.
     * Computed in Service — not stored in DB.
     */
    private int resultCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}