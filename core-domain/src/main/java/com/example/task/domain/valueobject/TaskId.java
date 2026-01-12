package com.example.task.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public final class TaskId {

    private final UUID value;

    public TaskId(UUID value) {
        this.value = Objects.requireNonNull(value, "TaskId value must not be null");
    }

    public static TaskId generate() {
        return new TaskId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskId)) return false;
        TaskId taskId = (TaskId) o;
        return value.equals(taskId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
