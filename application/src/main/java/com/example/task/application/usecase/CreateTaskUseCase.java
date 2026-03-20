package com.example.task.application.usecase;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.model.Task;
import com.example.task.domain.valueobject.TaskId;
import org.springframework.transaction.annotation.Transactional;

public class CreateTaskUseCase {

    private final TaskRepository taskRepository;

    public CreateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskId execute(String title, String description) {
        String trimmedTitle = title == null ? null : title.strip();
        String trimmedDescription = description == null ? null : description.strip();
        Task task = new Task(TaskId.generate(), trimmedTitle, trimmedDescription);
        taskRepository.save(task);
        return task.getId();
    }
}
