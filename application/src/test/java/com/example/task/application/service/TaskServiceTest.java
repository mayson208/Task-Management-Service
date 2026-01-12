package com.example.task.application.service;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskServiceTest {

    private static class InMemoryTaskRepository implements TaskRepository {
        private final Map<TaskId, Task> store = new HashMap<>();

        @Override
        public void save(Task task) {
            store.put(task.getId(), task);
        }

        @Override
        public Optional<Task> findById(TaskId id) {
            return Optional.ofNullable(store.get(id));
        }
    }

    @Test
    void shouldCreateAndCompleteTask() {
        TaskRepository repository = new InMemoryTaskRepository();
        TaskService service = new TaskService(repository);

        TaskId taskId = service.createTask("Ship application layer");

        service.completeTask(taskId);

        Task task = repository.findById(taskId).orElseThrow();
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }
}
