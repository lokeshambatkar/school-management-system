package com.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO for incoming Exam creation/update requests.
 *
 * Notice: we accept 'courseId' (a Long), NOT the full Course object.
 * WHY?
 *  - The client only needs to tell us WHICH course this exam belongs to.
 *  - We look up the Course entity in the Service layer using courseId.
 *  - Accepting a full Course object from the client would be a security risk
 *    (client could send manipulated Course data).
 *  - This pattern (ID reference instead of nested object) is standard REST practice.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamRequest {

    @NotBlank(message = "Exam title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    /**
     * @FutureOrPresent — an exam must be scheduled today or in the future.
     * (When CREATING. Update logic may relax this — handled in Service.)
     * Optional — some schools schedule exams without a fixed date initially.
     */
    @FutureOrPresent(message = "Exam date must be today or a future date")
    private LocalDate examDate;

    /**
     * Total marks must be a positive number.
     * @Min(1) prevents 0 or negative marks, which are nonsensical.
     */
    @NotNull(message = "Total marks are required")
    @Min(value = 1, message = "Total marks must be at least 1")
    @Max(value = 1000, message = "Total marks must not exceed 1000")
    private Integer totalMarks;

    /**
     * The ID of the Course this exam belongs to.
     * @NotNull — every exam MUST belong to a course.
     * The Service will validate that this courseId actually exists in DB.
     */
    @NotNull(message = "Course ID is required")
    private Long courseId;
}