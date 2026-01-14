package com.example.task.api.controller;

import com.example.task.api.dto.CreateTaskRequest;
import com.example.task.api.dto.CreateTaskResponse;
import com.example.task.application.service.TaskService;
import com.example.task.domain.valueobject.TaskId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Task management operations")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create a new task")
    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request
    ) {
        TaskId taskId = taskService.createTask(request.getTitle());
        return ResponseEntity.ok(
                new CreateTaskResponse(taskId.getValue())
        );
    }

    @Operation(summary = "Mark a task as completed")
    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable UUID id) {
        taskService.completeTask(new TaskId(id));
        return ResponseEntity.noContent().build();
    }
}
