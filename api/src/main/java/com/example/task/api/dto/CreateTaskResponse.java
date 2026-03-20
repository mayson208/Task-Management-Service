package com.example.task.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Response returned after successfully creating a task")
public class CreateTaskResponse {

    @Schema(description = "Unique identifier of the created task", example = "123e4567-e89b-12d3-a456-426614174000")
    private final UUID id;

    public CreateTaskResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() { return id; }
}
