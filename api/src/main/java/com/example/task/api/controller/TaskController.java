package com.example.task.api.controller;

import com.example.task.api.dto.*;
import com.example.task.application.service.TaskService;
import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Task management operations")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create a new task")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Task created"),
        @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        TaskId id = taskService.createTask(request.getTitle(), request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateTaskResponse(id.getValue()));
    }

    @Operation(summary = "Get all tasks with optional filtering and pagination")
    @GetMapping
    public ResponseEntity<PagedTaskResponse> getTasks(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filter by status") @RequestParam(required = false) TaskStatus status
    ) {
        List<TaskResponse> tasks = taskService.getTasks(page, size, status)
                .stream()
                .map(this::toResponse)
                .toList();

        long total = taskService.countTasks(status);
        return ResponseEntity.ok(new PagedTaskResponse(tasks, page, size, total));
    }

    @Operation(summary = "Get a task by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task found"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable UUID id) {
        Task task = taskService.getTaskById(new TaskId(id));
        return ResponseEntity.ok(toResponse(task));
    }

    @Operation(summary = "Update a task's title or description")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task updated"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTaskRequest request
    ) {
        taskService.updateTask(new TaskId(id), request.getTitle(), request.getDescription());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark a task as in progress")
    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startTask(@PathVariable UUID id) {
        taskService.markInProgress(new TaskId(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark a task as completed")
    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable UUID id) {
        taskService.completeTask(new TaskId(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a task")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task deleted"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(new TaskId(id));
        return ResponseEntity.noContent().build();
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
