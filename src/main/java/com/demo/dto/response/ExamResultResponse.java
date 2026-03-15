package com.demo.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO returned to the client after any ExamResult operation.
 *
 * DESIGN DECISIONS:
 *
 * 1. 'grade' IS included in the response (but NOT in the request).
 *    WHY? Grade is COMPUTED by the server (Service layer) and returned to client.
 *    The client never sends a grade — they only see it in the response.
 *    This is an example of server-side business logic vs client-provided data.
 *
 * 2. We flatten Student and Exam info into simple fields.
 *    WHY? Avoids nesting hell. The client sees:
 *      "studentName": "John Doe"  ← clean, readable
 *    Instead of:
 *      "student": { "id": 1, "firstName": "John", "lastName": "Doe", "email": "..." }  ← noisy
 *
 * 3. We include 'percentage' — computed from marksObtained / totalMarks * 100.
 *    WHY? The client could compute this, but it's better UX to do it server-side.
 *    Also avoids division-by-zero bugs on the frontend.
 *    This is a "derived field" — not stored in DB, computed in Service.
 *
 * GRADE LOGIC (implemented in Service layer):
 *    percentage >= 90  → A
 *    percentage >= 75  → B
 *    percentage >= 60  → C
 *    percentage >= 40  → D
 *    percentage < 40   → F
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultResponse {

    private Long id;

    /**
     * Marks the student scored.
     */
    private Double marksObtained;

    /**
     * Total marks for this exam — included for context.
     * Client can display: "23.5 / 50"
     */
    private Integer totalMarks;

    /**
     * Derived field: (marksObtained / totalMarks) * 100
     * Rounded to 2 decimal places in the Service.
     */
    private Double percentage;

    /**
     * Computed grade: A, B, C, D, or F.
     * Computed in Service layer, NOT accepted from client.
     */
    private String grade;

    private String remarks;

    // --- Flattened Student Info ---
    private Long studentId;
    private String studentFirstName;
    private String studentLastName;
    private String studentEmail;

    // --- Flattened Exam Info ---
    private Long examId;
    private String examTitle;
    private Long courseId;
    private String courseTitle;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}