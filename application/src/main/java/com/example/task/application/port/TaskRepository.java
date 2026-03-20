package com.example.task.application.port;

import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    void save(Task task);

    Optional<Task> findById(TaskId id);

    List<Task> findAll(int page, int size, TaskStatus status);

    long countAll(TaskStatus status);

    void deleteById(TaskId id);
}
