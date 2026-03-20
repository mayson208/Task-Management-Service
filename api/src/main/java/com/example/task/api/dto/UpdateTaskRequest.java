package com.example.task.api.dto;

import jakarta.validation.constraints.Size;

public class UpdateTaskRequest {

    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    protected UpdateTaskRequest() {}

    public String getTitle() { return title; }
    public String getDescription() { return description; }
}
