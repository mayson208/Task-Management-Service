package com.example.task.api.dto;

import java.util.UUID;

public class CreateTaskResponse {

    private final UUID id;

    public CreateTaskResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
