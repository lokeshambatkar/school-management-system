package com.demo.exception;

/**
 * Thrown when a requested resource does not exist in the database.
 * Maps to HTTP 404 Not Found in the GlobalExceptionHandler.
 *
 * WHY extend RuntimeException (unchecked) instead of Exception (checked)?
 *   - Checked exceptions force every caller to catch or re-throw — very noisy.
 *   - Spring @Transactional rolls back on RuntimeException by default.
 *   - Modern Java best practice (Spring, Hibernate) uses unchecked exceptions.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Usage:  throw new ResourceNotFoundException("Student", "id", 42L);
     * Produces: "Student not found with id : '42'"
     *
     * WHY a structured 3-arg constructor?
     *   - Produces consistent, readable error messages everywhere.
     *   - Controller advice can log resourceName for monitoring/alerting.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}