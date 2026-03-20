package com.example.task.api.controller;

import com.example.task.api.dto.*;
import com.example.task.application.usecase.*;
import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Task management operations")
@Validated
public class TaskController {

    private final CreateTaskUseCase createTask;
    private final QueryTasksUseCase queryTasks;
    private final UpdateTaskUseCase updateTask;
    private final ChangeTaskStatusUseCase changeTaskStatus;
    private final DeleteTaskUseCase deleteTask;

    public TaskController(CreateTaskUseCase createTask,
                          QueryTasksUseCase queryTasks,
                          UpdateTaskUseCase updateTask,
                          ChangeTaskStatusUseCase changeTaskStatus,
                          DeleteTaskUseCase deleteTask) {
        this.createTask = createTask;
        this.queryTasks = queryTasks;
        this.updateTask = updateTask;
        this.changeTaskStatus = changeTaskStatus;
        this.deleteTask = deleteTask;
    }

    @Operation(summary = "Create a new task")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Task created"),
        @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        TaskId id = createTask.execute(request.getTitle(), request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateTaskResponse(id.getValue()));
    }

    @Operation(summary = "Get all tasks with optional filtering and pagination")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tasks retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping
    public ResponseEntity<PagedTaskResponse> getTasks(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size (1-100)") @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @Parameter(description = "Filter by status") @RequestParam(required = false) TaskStatus status
    ) {
        List<TaskResponse> tasks = queryTasks.list(page, size, status)
                .stream()
                .map(this::toResponse)
                .toList();
        long total = queryTasks.count(status);
        return ResponseEntity.ok(new PagedTaskResponse(tasks, page, size, total));
    }

    @Operation(summary = "Get a task by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task found"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable UUID id) {
        Task task = queryTasks.getById(new TaskId(id));
        return ResponseEntity.ok(toResponse(task));
    }

    @Operation(summary = "Update a task's title or description")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task updated"),
        @ApiResponse(responseCode = "400", description = "Invalid request or no fields provided"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTaskRequest request
    ) {
        updateTask.execute(new TaskId(id), request.getTitle(), request.getDescription());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark a task as in progress")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task marked as in progress"),
        @ApiResponse(responseCode = "404", description = "Task not found"),
        @ApiResponse(responseCode = "409", description = "Task cannot be started in its current state")
    })
    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startTask(@PathVariable UUID id) {
        changeTaskStatus.markInProgress(new TaskId(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark a task as completed")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task marked as completed"),
        @ApiResponse(responseCode = "404", description = "Task not found"),
        @ApiResponse(responseCode = "409", description = "Task is already completed")
    })
    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable UUID id) {
        changeTaskStatus.markCompleted(new TaskId(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a task")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task deleted"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        deleteTask.execute(new TaskId(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get aggregated task statistics")
    @ApiResponse(responseCode = "200", description = "Statistics retrieved")
    @GetMapping("/stats")
    public ResponseEntity<TaskStatsResponse> getStats() {
        long total      = queryTasks.count(null);
        long pending    = queryTasks.count(TaskStatus.PENDING);
        long inProgress = queryTasks.count(TaskStatus.IN_PROGRESS);
        long completed  = queryTasks.count(TaskStatus.COMPLETED);
        return ResponseEntity.ok(new TaskStatsResponse(total, pending, inProgress, completed));
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId().getValue(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
