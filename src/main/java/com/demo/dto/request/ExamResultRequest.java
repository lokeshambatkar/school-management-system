package com.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO for incoming ExamResult creation/update requests.
 *
 * IMPORTANT DESIGN DECISIONS:
 *
 * 1. We do NOT accept 'grade' from the client.
 *    WHY? Grade is COMPUTED in the Service layer based on marksObtained
 *    vs totalMarks of the exam. The client should never dictate the grade.
 *    This is a business rule — it belongs in the Service, not the DTO.
 *
 * 2. We do NOT accept 'createdAt' or 'updatedAt'.
 *    WHY? These are server-controlled audit fields.
 *
 * 3. Duplicate check (same student + same exam) is done in the Service layer.
 *    The DB has a unique constraint as a safety net, but we check first
 *    to return a meaningful error message, not a cryptic DB error.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultRequest {

    /**
     * @NotNull for Double — use @NotBlank only for Strings.
     * @DecimalMin — marks can't be negative (0 is valid: student attempted but scored nothing).
     * The upper bound is validated against exam's totalMarks in the Service layer
     * (we can't do that here since we don't have the Exam object at validation time).
     */
    @NotNull(message = "Marks obtained are required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Marks obtained cannot be negative")
    private Double marksObtained;

    /**
     * Remarks are optional — teacher may or may not leave a comment.
     * Size limit prevents text abuse.
     */
    @Size(max = 300, message = "Remarks must not exceed 300 characters")
    private String remarks;

    /**
     * Which student is this result for?
     * @NotNull — a result without a student is meaningless.
     */
    @NotNull(message = "Student ID is required")
    private Long studentId;

    /**
     * Which exam is this result for?
     * @NotNull — a result without an exam is meaningless.
     */
    @NotNull(message = "Exam ID is required")
    private Long examId;
}