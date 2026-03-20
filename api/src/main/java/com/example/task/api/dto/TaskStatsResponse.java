package com.example.task.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Aggregated task statistics")
public class TaskStatsResponse {

    private final long totalTasks;
    private final long pendingTasks;
    private final long inProgressTasks;
    private final long completedTasks;

    public TaskStatsResponse(long totalTasks, long pendingTasks, long inProgressTasks, long completedTasks) {
        this.totalTasks = totalTasks;
        this.pendingTasks = pendingTasks;
        this.inProgressTasks = inProgressTasks;
        this.completedTasks = completedTasks;
    }

    public long getTotalTasks()      { return totalTasks; }
    public long getPendingTasks()    { return pendingTasks; }
    public long getInProgressTasks() { return inProgressTasks; }
    public long getCompletedTasks()  { return completedTasks; }
}
