package com.example.task.api.config;

import com.example.task.application.port.TaskRepository;
import com.example.task.application.service.TaskService;
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
    public TaskService taskService(TaskRepository taskRepository) {
        return new TaskService(taskRepository);
    }
}
