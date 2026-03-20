package com.example.task.application.usecase;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.exception.TaskNotFoundException;
import com.example.task.domain.model.Task;
import com.example.task.domain.valueobject.TaskId;
import org.springframework.transaction.annotation.Transactional;

public class UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void execute(TaskId id, String title, String description) {
        if (title == null && description == null) {
            throw new IllegalArgumentException("At least one field must be provided");
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if (title != null) task.updateTitle(title.strip());
        if (description != null) task.updateDescription(description.strip());
        taskRepository.save(task);
    }
}
