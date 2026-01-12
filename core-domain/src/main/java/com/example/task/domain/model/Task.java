package com.example.task.domain.model;

import com.example.task.domain.valueobject.TaskId;
import com.example.task.domain.exception.InvalidTaskStateException;

import java.util.Objects;

public class Task {

    private final TaskId id;
    private String title;
    private TaskStatus status;

    public Task(TaskId id, String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidTaskStateException("Task title must not be blank");
        }
        this.id = id;
        this.title = title;
        this.status = TaskStatus.PENDING;
    }

    public TaskId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void markCompleted() {
        if (this.status == TaskStatus.COMPLETED) {
            throw new InvalidTaskStateException("Task is already completed");
        }
        this.status = TaskStatus.COMPLETED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
