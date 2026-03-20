package com.example.task.domain.model;

import com.example.task.domain.exception.InvalidTaskStateException;
import com.example.task.domain.valueobject.TaskId;

import java.time.Instant;
import java.util.Objects;

public class Task {

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
        if (this.status == TaskStatus.COMPLETED) {
            throw new InvalidTaskStateException("Cannot restart a completed task");
        }
        if (this.status == TaskStatus.IN_PROGRESS) {
            throw new InvalidTaskStateException("Task is already in progress");
        }
        this.status = TaskStatus.IN_PROGRESS;
        this.updatedAt = Instant.now();
    }

    public void markCompleted() {
        if (this.status == TaskStatus.COMPLETED) {
            throw new InvalidTaskStateException("Task is already completed");
        }
        this.status = TaskStatus.COMPLETED;
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
