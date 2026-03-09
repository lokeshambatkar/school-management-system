package com.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO for incoming Student creation/update requests.
 *
 * WHY a separate DTO instead of using the Entity directly?
 *  - Entities are tied to the DB schema. If you expose them directly,
 *    any DB change breaks your API contract.
 *  - DTOs let you validate ONLY what the client should send
 *    (e.g., client should NOT send createdAt — the server sets it).
 *  - Prevents mass-assignment attacks (client can't set internal fields).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    /**
     * @NotBlank = not null AND not empty AND not just whitespace.
     * Use this for Strings. Never use @NotNull alone for Strings.
     * @Size limits the length — protects your DB column and prevents abuse.
     */
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    /**
     * @Email validates the format (contains @, domain, etc.)
     * @NotBlank ensures it's not empty.
     * Uniqueness is checked in the Service layer — not here.
     * WHY? Validation annotations only check FORMAT, not business rules.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid format")
    private String email;

    /**
     * Phone is optional (no @NotBlank).
     * @Pattern enforces a simple digit-based format.
     * The regex allows 7–15 digits — international-friendly.
     */
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Phone must be 7 to 15 digits")
    private String phone;

    /**
     * @Past ensures the date of birth is in the past.
     * A student born today or in the future makes no sense.
     * Optional field — null is allowed.
     */
    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;
}