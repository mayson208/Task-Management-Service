package com.example.task.api.config;

import com.example.task.application.port.TaskRepository;
import com.example.task.application.usecase.*;
import com.example.task.persistence.repository.JpaTaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public TaskRepository taskRepository() {
        return new JpaTaskRepository(entityManager);
    }

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository) {
        return new CreateTaskUseCase(taskRepository);
    }

    @Bean
    public QueryTasksUseCase queryTasksUseCase(TaskRepository taskRepository) {
        return new QueryTasksUseCase(taskRepository);
    }

    @Bean
    public UpdateTaskUseCase updateTaskUseCase(TaskRepository taskRepository) {
        return new UpdateTaskUseCase(taskRepository);
    }

    @Bean
    public ChangeTaskStatusUseCase changeTaskStatusUseCase(TaskRepository taskRepository) {
        return new ChangeTaskStatusUseCase(taskRepository);
    }

    @Bean
    public DeleteTaskUseCase deleteTaskUseCase(TaskRepository taskRepository) {
        return new DeleteTaskUseCase(taskRepository);
    }
}
