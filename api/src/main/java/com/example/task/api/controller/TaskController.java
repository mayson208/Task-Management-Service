package com.example.task.api.controller;

import com.example.task.api.dto.CreateTaskRequest;
import com.example.task.api.dto.CreateTaskResponse;
import com.example.task.application.service.TaskService;
import com.example.task.domain.valueobject.TaskId;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request
    ) {
        TaskId taskId = taskService.createTask(request.getTitle());
        return ResponseEntity.ok(
                new CreateTaskResponse(taskId.getValue())
        );
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable UUID id) {
        taskService.completeTask(new TaskId(id));
        return ResponseEntity.noContent().build();
    }
}
