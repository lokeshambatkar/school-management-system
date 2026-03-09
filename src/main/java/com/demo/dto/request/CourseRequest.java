package com.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO for incoming Course creation/update requests.
 *
 * WHY no 'students' or 'exams' field here?
 *  - Enrollment (adding students to a course) is a separate operation
 *    with its own endpoint. Mixing it here violates Single Responsibility.
 *  - Exams are created independently via ExamRequest.
 *  - Keeping this lean = clear API contract.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {

    /**
     * Title must be unique (enforced at DB level via @Column(unique=true)).
     * We also check uniqueness in the Service layer to give a friendly error
     * before hitting the DB constraint.
     */
    @NotBlank(message = "Course title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    /**
     * Description is optional — no @NotBlank.
     * But we limit size to prevent abuse.
     */
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Instructor name is required")
    @Size(min = 2, max = 100, message = "Instructor name must be between 2 and 100 characters")
    private String instructorName;

    /**
     * @NotNull for Integer/primitives (not @NotBlank — that's for Strings).
     * @Min(1) — a course with 0 credits makes no academic sense.
     * @Max(10) — a reasonable upper limit; adjust per your domain rules.
     */
    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 10, message = "Credits must not exceed 10")
    private Integer credits;
}