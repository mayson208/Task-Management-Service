package com.example.task.persistence.repository;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.model.Task;
import com.example.task.domain.valueobject.TaskId;
import com.example.task.persistence.entity.TaskEntity;
import com.example.task.persistence.mapper.TaskEntityMapper;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class JpaTaskRepository implements TaskRepository {

    private final EntityManager entityManager;

    public JpaTaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Task task) {
        TaskEntity entity = TaskEntityMapper.toEntity(task);
        entityManager.merge(entity);
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        TaskEntity entity = entityManager.find(TaskEntity.class, id.getValue());
        return Optional.ofNullable(entity)
                .map(TaskEntityMapper::toDomain);
    }
}
