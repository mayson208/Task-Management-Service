package com.example.task.application.usecase;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.exception.TaskNotFoundException;
import com.example.task.domain.valueobject.TaskId;
import org.springframework.transaction.annotation.Transactional;

public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void execute(TaskId id) {
        if (taskRepository.findById(id).isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}
