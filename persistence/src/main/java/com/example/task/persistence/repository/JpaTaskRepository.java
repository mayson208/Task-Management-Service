package com.example.task.persistence.repository;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import com.example.task.persistence.entity.TaskEntity;
import com.example.task.persistence.mapper.TaskEntityMapper;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JpaTaskRepository implements TaskRepository {

    private final EntityManager entityManager;

    public JpaTaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Task task) {
        entityManager.merge(TaskEntityMapper.toEntity(task));
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        TaskEntity entity = entityManager.find(TaskEntity.class, id.getValue());
        return Optional.ofNullable(entity).map(TaskEntityMapper::toDomain);
    }

    @Override
    public List<Task> findAll(int page, int size, TaskStatus status) {
        String jpql = "SELECT t FROM TaskEntity t"
                + (status != null ? " WHERE t.status = :status" : "")
                + " ORDER BY t.createdAt DESC";

        var query = entityManager.createQuery(jpql, TaskEntity.class);
        if (status != null) {
            query.setParameter("status", status.name());
        }

        return query
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(TaskEntityMapper::toDomain)
                .toList();
    }

    @Override
    public long countAll(TaskStatus status) {
        String jpql = "SELECT COUNT(t) FROM TaskEntity t"
                + (status != null ? " WHERE t.status = :status" : "");

        var query = entityManager.createQuery(jpql, Long.class);
        if (status != null) {
            query.setParameter("status", status.name());
        }

        return query.getSingleResult();
    }

    @Override
    public void deleteById(TaskId id) {
        UUID uuid = id.getValue();
        entityManager.createQuery("DELETE FROM TaskEntity t WHERE t.id = :id")
                .setParameter("id", uuid)
                .executeUpdate();
    }
}
