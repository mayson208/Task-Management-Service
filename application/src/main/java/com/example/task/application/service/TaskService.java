package com.example.task.application.service;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.exception.TaskNotFoundException;
import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskId createTask(String title, String description) {
        Task task = new Task(TaskId.generate(), title, description);
        taskRepository.save(task);
        return task.getId();
    }

    @Transactional(readOnly = true)
    public Task getTaskById(TaskId id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Task> getTasks(int page, int size, TaskStatus status) {
        return taskRepository.findAll(page, size, status);
    }

    @Transactional(readOnly = true)
    public long countTasks(TaskStatus status) {
        return taskRepository.countAll(status);
    }

    @Transactional
    public void updateTask(TaskId id, String title, String description) {
        if (title == null && description == null) {
            throw new IllegalArgumentException("At least one field must be provided");
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if (title != null) task.updateTitle(title);
        if (description != null) task.updateDescription(description);
        taskRepository.save(task);
    }

    @Transactional
    public void markInProgress(TaskId id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.markInProgress();
        taskRepository.save(task);
    }

    @Transactional
    public void completeTask(TaskId id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.markCompleted();
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(TaskId id) {
        if (taskRepository.findById(id).isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}
