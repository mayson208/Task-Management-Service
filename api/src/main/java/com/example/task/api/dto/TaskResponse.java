package com.example.task.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Full representation of a task")
public class TaskResponse {

    @Schema(description = "Unique identifier of the task", example = "123e4567-e89b-12d3-a456-426614174000")
    private final UUID id;

    @Schema(description = "Title of the task", example = "Fix login bug")
    private final String title;

    @Schema(description = "Description of the task", example = "Users are unable to log in on mobile devices")
    private final String description;

    @Schema(description = "Current status of the task", example = "PENDING", allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED"})
    private final String status;

    @Schema(description = "Timestamp when the task was created")
    private final Instant createdAt;

    @Schema(description = "Timestamp when the task was last updated")
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
