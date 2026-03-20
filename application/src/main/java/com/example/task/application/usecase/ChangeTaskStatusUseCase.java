package com.example.task.application.usecase;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.exception.TaskNotFoundException;
import com.example.task.domain.model.Task;
import com.example.task.domain.valueobject.TaskId;
import org.springframework.transaction.annotation.Transactional;

public class ChangeTaskStatusUseCase {

    private final TaskRepository taskRepository;

    public ChangeTaskStatusUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void markInProgress(TaskId id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.markInProgress();
        taskRepository.save(task);
    }

    @Transactional
    public void markCompleted(TaskId id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.markCompleted();
        taskRepository.save(task);
    }
}
