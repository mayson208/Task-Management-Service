package com.example.task.api.dto;

import java.util.List;

public class PagedTaskResponse {

    private final List<TaskResponse> tasks;
    private final int page;
    private final int size;

    public PagedTaskResponse(List<TaskResponse> tasks, int page, int size) {
        this.tasks = tasks;
        this.page = page;
        this.size = size;
    }

    public List<TaskResponse> getTasks() {
        return tasks;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
