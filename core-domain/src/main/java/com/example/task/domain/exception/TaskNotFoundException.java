package com.example.task.domain.exception;

import com.example.task.domain.valueobject.TaskId;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(TaskId id) {
        super("Task not found with id: " + id.getValue());
    }
}
