package com.example.task.domain.model;

import com.example.task.domain.exception.InvalidTaskStateException;
import com.example.task.domain.valueobject.TaskId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task newTask(String title) {
        return new Task(TaskId.generate(), title, null);
    }

    @Test
    void newTask_withBlankTitle_throwsException() {
        assertThrows(InvalidTaskStateException.class, () -> newTask("   "));
    }

    @Test
    void newTask_withNullTitle_throwsException() {
        assertThrows(InvalidTaskStateException.class, () -> newTask(null));
    }

    @Test
    void markInProgress_fromPending_succeeds() {
        Task task = newTask("My task");
        task.markInProgress();
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    void markInProgress_whenAlreadyInProgress_throws() {
        Task task = newTask("My task");
        task.markInProgress();
        assertThrows(InvalidTaskStateException.class, task::markInProgress);
    }

    @Test
    void markInProgress_whenCompleted_throws() {
        Task task = newTask("My task");
        task.markCompleted();
        assertThrows(InvalidTaskStateException.class, task::markInProgress);
    }

    @Test
    void markCompleted_fromPending_succeeds() {
        Task task = newTask("My task");
        task.markCompleted();
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    void markCompleted_fromInProgress_succeeds() {
        Task task = newTask("My task");
        task.markInProgress();
        task.markCompleted();
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    void markCompleted_whenAlreadyCompleted_throws() {
        Task task = newTask("My task");
        task.markCompleted();
        assertThrows(InvalidTaskStateException.class, task::markCompleted);
    }

    @Test
    void reconstitute_setsStatusDirectly() {
        TaskId id = TaskId.generate();
        Instant now = Instant.now();

        Task task = Task.reconstitute(id, "My task", "desc", TaskStatus.COMPLETED, now, now);

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
        assertEquals("My task", task.getTitle());
        assertEquals(id, task.getId());
    }
}
