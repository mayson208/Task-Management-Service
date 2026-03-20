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
        return Task.reconstitute(
                new TaskId(entity.getId()),
                entity.getTitle(),
                entity.getDescription(),
                TaskStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
