package com.example.task.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Paginated list of tasks")
public class PagedTaskResponse {

    @Schema(description = "List of tasks on the current page")
    private final List<TaskResponse> tasks;

    @Schema(description = "Current page number (0-based)", example = "0")
    private final int page;

    @Schema(description = "Number of tasks per page", example = "10")
    private final int size;

    @Schema(description = "Total number of tasks matching the query", example = "42")
    private final long totalElements;

    @Schema(description = "Total number of pages", example = "5")
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
