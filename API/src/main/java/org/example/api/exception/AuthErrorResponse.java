package org.example.api.exception;

public record AuthErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
