package com.taskapi.domain;

import java.util.UUID;

public final class Task {
    private final TaskIdentity identity;
    private TaskState state;

    private Task(TaskIdentity identity, TaskState state) {
        this.identity = identity;
        this.state = state;
    }

    public static Task create(String title, String description) {
        String identifier = UUID.randomUUID().toString();
        TaskIdentity identity = new TaskIdentity(identifier, title);
        TaskState state = new TaskState(description, TaskStatus.PENDING);
        return new Task(identity, state);
    }

    public void complete() {
        state = state.transition(TaskStatus.COMPLETED);
    }

    public boolean matches(TaskStatus status) {
        return state.matches(status);
    }

    public void accept(ITaskVisitor visitor) {
        visitor.visit(identity, state);
    }
}
