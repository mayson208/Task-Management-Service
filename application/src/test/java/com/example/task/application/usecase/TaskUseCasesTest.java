package com.example.task.application.usecase;

import com.example.task.application.port.TaskRepository;
import com.example.task.domain.exception.InvalidTaskStateException;
import com.example.task.domain.exception.TaskNotFoundException;
import com.example.task.domain.model.Task;
import com.example.task.domain.model.TaskStatus;
import com.example.task.domain.valueobject.TaskId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskUseCasesTest {

    private static class InMemoryTaskRepository implements TaskRepository {
        private final Map<TaskId, Task> store = new HashMap<>();

        @Override
        public void save(Task task) { store.put(task.getId(), task); }

        @Override
        public Optional<Task> findById(TaskId id) { return Optional.ofNullable(store.get(id)); }

        @Override
        public List<Task> findAll(int page, int size, TaskStatus status) {
            return store.values().stream()
                    .filter(t -> status == null || t.getStatus() == status)
                    .skip((long) page * size)
                    .limit(size)
                    .toList();
        }

        @Override
        public long countAll(TaskStatus status) {
            return store.values().stream()
                    .filter(t -> status == null || t.getStatus() == status)
                    .count();
        }

        @Override
        public void deleteById(TaskId id) { store.remove(id); }
    }

    private TaskRepository repository;
    private CreateTaskUseCase createTask;
    private QueryTasksUseCase queryTasks;
    private UpdateTaskUseCase updateTask;
    private ChangeTaskStatusUseCase changeTaskStatus;
    private DeleteTaskUseCase deleteTask;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
        createTask = new CreateTaskUseCase(repository);
        queryTasks = new QueryTasksUseCase(repository);
        updateTask = new UpdateTaskUseCase(repository);
        changeTaskStatus = new ChangeTaskStatusUseCase(repository);
        deleteTask = new DeleteTaskUseCase(repository);
    }

    @Test
    void createTask_persistsAndReturnsId() {
        TaskId id = createTask.execute("My task", null);

        assertNotNull(id);
        assertEquals("My task", queryTasks.getById(id).getTitle());
    }

    @Test
    void createTask_trimsWhitespaceFromTitle() {
        TaskId id = createTask.execute("  trimmed  ", null);

        assertEquals("trimmed", queryTasks.getById(id).getTitle());
    }

    @Test
    void createTask_andComplete_succeeds() {
        TaskId id = createTask.execute("Ship it", null);
        changeTaskStatus.markCompleted(id);

        assertEquals(TaskStatus.COMPLETED, queryTasks.getById(id).getStatus());
    }

    @Test
    void getTaskById_whenNotFound_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> queryTasks.getById(TaskId.generate()));
    }

    @Test
    void updateTask_withBothFieldsNull_throwsIllegalArgumentException() {
        TaskId id = createTask.execute("Original", null);
        assertThrows(IllegalArgumentException.class, () -> updateTask.execute(id, null, null));
    }

    @Test
    void updateTask_trimsWhitespaceFromTitle() {
        TaskId id = createTask.execute("Original", null);
        updateTask.execute(id, "  updated  ", null);

        assertEquals("updated", queryTasks.getById(id).getTitle());
    }

    @Test
    void changeStatus_whenAlreadyCompleted_throwsInvalidTaskStateException() {
        TaskId id = createTask.execute("My task", null);
        changeTaskStatus.markCompleted(id);
        assertThrows(InvalidTaskStateException.class, () -> changeTaskStatus.markInProgress(id));
    }

    @Test
    void deleteTask_whenNotFound_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> deleteTask.execute(TaskId.generate()));
    }

    @Test
    void deleteTask_removesTask() {
        TaskId id = createTask.execute("To delete", null);
        deleteTask.execute(id);
        assertThrows(TaskNotFoundException.class, () -> queryTasks.getById(id));
    }
}
