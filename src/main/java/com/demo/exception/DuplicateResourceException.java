package com.demo.exception;

/**
 * Thrown when a client tries to create a resource that already exists.
 * Maps to HTTP 409 Conflict in the GlobalExceptionHandler.
 *
 * Example: creating a student with an email that already exists in the DB.
 *
 * WHY a separate exception class instead of using IllegalArgumentException?
 *   - Gives the GlobalExceptionHandler a precise type to catch.
 *   - You can map it to exactly the right HTTP status (409 Conflict).
 *   - Makes the codebase self-documenting — the intent is crystal clear.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}