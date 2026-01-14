package com.example.task.api.dto;

import java.util.UUID;

import com.example.task.domain.model.TaskStatus;

import java.util.List;

List<Task> findAll(int page, int size, TaskStatus status);

public class TaskResponse {

    private final UUID id;
    private final String title;
    private final String status;

    public TaskResponse(UUID id, String title, String status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }
}
