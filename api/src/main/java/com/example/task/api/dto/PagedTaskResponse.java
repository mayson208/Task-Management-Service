package com.example.task.api.dto;

import java.util.List;

public class PagedTaskResponse {

    private final List<TaskResponse> tasks;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PagedTaskResponse(List<TaskResponse> tasks, int page, int size, long totalElements) {
        this.tasks = tasks;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 0;
    }

    public List<TaskResponse> getTasks() { return tasks; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
}
