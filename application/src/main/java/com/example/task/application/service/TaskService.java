package com.example.task.application.service;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.model.Task;
import com.example.task.domain.valueobject.TaskId;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskId createTask(String title) {
        Task task = new Task(TaskId.generate(), title);
        taskRepository.save(task);
        return task.getId();
    }

    public void completeTask(TaskId taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.markCompleted();
        taskRepository.save(task);
    }
}
