package com.example.task.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for updating a task. At least one field must be provided.")
public class UpdateTaskRequest {

    @Schema(description = "New title for the task", example = "Fix login bug on Android")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Schema(description = "New description for the task", example = "Affects Android 12 and above")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    protected UpdateTaskRequest() {}

    public String getTitle() { return title; }
    public String getDescription() { return description; }
}
