package com.example.task.persistence.mapper;

import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import com.example.task.persistence.entity.TaskEntity;

public class TaskEntityMapper {

    public static TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getId().getValue(),
                task.getTitle(),
                task.getStatus().name()
        );
    }

    public static Task toDomain(TaskEntity entity) {
        Task task = new Task(
                new TaskId(entity.getId()),
                entity.getTitle()
        );

        if (TaskStatus.valueOf(entity.getStatus()) == TaskStatus.COMPLETED) {
            task.markCompleted();
        }

        return task;
    }
}
