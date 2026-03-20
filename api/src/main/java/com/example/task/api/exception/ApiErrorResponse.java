package com.example.task.api.exception;

import java.time.Instant;
import java.util.List;

public class ApiErrorResponse {

    private final int status;
    private final String message;
    private final List<String> errors;
    private final Instant timestamp;

    public ApiErrorResponse(int status, String message) {
        this(status, message, List.of());
    }

    public ApiErrorResponse(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = Instant.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
