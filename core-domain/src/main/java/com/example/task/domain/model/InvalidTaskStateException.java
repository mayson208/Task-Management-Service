package com.example.task.domain.exception;

public class InvalidTaskStateException extends RuntimeException {

    public InvalidTaskStateException(String message) {
        super(message);
    }
}
