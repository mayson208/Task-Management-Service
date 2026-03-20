package com.example.task.domain.model;

public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;

    public boolean canTransitionTo(TaskStatus next) {
        return switch (this) {
            case PENDING -> next == IN_PROGRESS || next == COMPLETED;
            case IN_PROGRESS -> next == COMPLETED;
            case COMPLETED -> false;
        };
    }
}
