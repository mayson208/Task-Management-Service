package com.example.task.api.dto;

import java.time.Instant;
import java.util.UUID;

public class TaskResponse {

    private final UUID id;
    private final String title;
    private final String description;
    private final String status;
    private final Instant createdAt;
    private final Instant updatedAt;

    public TaskResponse(UUID id, String title, String description, String status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
