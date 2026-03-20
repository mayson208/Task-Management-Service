package com.example.task.domain.model;

import com.example.task.domain.exception.InvalidTaskStateException;
import com.example.task.domain.valueobject.TaskId;

import java.time.Instant;
import java.util.Objects;

public class Task {

    /**
     * Reconstitutes a Task from persistent storage, bypassing state machine validation.
     * Use only in persistence mappers — not for creating new tasks.
     */
    public static Task reconstitute(TaskId id, String title, String description,
                                    TaskStatus status, Instant createdAt, Instant updatedAt) {
        Task task = new Task(id, title, description, status, createdAt, updatedAt);
        return task;
    }

    private Task(TaskId id, String title, String description,
                 TaskStatus status, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id, "TaskId must not be null");
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private final TaskId id;
    private String title;
    private String description;
    private TaskStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Task(TaskId id, String title, String description) {
        if (title == null || title.isBlank()) {
            throw new InvalidTaskStateException("Task title must not be blank");
        }
        this.id = Objects.requireNonNull(id, "TaskId must not be null");
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public void markInProgress() {
        transitionTo(TaskStatus.IN_PROGRESS);
    }

    public void markCompleted() {
        transitionTo(TaskStatus.COMPLETED);
    }

    private void transitionTo(TaskStatus next) {
        if (!this.status.canTransitionTo(next)) {
            throw new InvalidTaskStateException(
                    String.format("Cannot transition task from %s to %s", this.status, next));
        }
        this.status = next;
        this.updatedAt = Instant.now();
    }

    public void updateTitle(String newTitle) {
        if (newTitle == null || newTitle.isBlank()) {
            throw new InvalidTaskStateException("Task title must not be blank");
        }
        this.title = newTitle;
        this.updatedAt = Instant.now();
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
        this.updatedAt = Instant.now();
    }

    public TaskId getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
