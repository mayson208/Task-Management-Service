package com.example.task.application.port;

import com.example.task.domain.model.Task;
import com.example.task.domain.valueobject.TaskId;

import java.util.Optional;

public interface TaskRepository {

    void save(Task task);

    Optional<Task> findById(TaskId id);
}
