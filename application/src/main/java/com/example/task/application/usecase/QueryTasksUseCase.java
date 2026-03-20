package com.example.task.application.usecase;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.exception.TaskNotFoundException;
import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class QueryTasksUseCase {

    private final TaskRepository taskRepository;

    public QueryTasksUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public Task getById(TaskId id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Task> list(int page, int size, TaskStatus status) {
        return taskRepository.findAll(page, size, status);
    }

    @Transactional(readOnly = true)
    public long count(TaskStatus status) {
        return taskRepository.countAll(status);
    }
}
