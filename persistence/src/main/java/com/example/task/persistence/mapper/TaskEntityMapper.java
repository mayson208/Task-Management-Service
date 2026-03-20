package com.example.task.persistence.mapper;

import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import com.example.task.persistence.entity.TaskEntity;

public class TaskEntityMapper {

    private TaskEntityMapper() {}

    public static TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getId().getValue(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    public static Task toDomain(TaskEntity entity) {
        Task task = new Task(
                new TaskId(entity.getId()),
                entity.getTitle(),
                entity.getDescription()
        );

        TaskStatus status = TaskStatus.valueOf(entity.getStatus());
        if (status == TaskStatus.IN_PROGRESS) {
            task.markInProgress();
        } else if (status == TaskStatus.COMPLETED) {
            task.markInProgress(); // transition through IN_PROGRESS first
            task.markCompleted();
        }

        return task;
    }
}
