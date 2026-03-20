package com.example.task.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for creating a new task")
public class CreateTaskRequest {

    @Schema(description = "Title of the task", example = "Fix login bug", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Schema(description = "Optional description of the task", example = "Users are unable to log in on mobile devices")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    protected CreateTaskRequest() {}

    public CreateTaskRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
}
