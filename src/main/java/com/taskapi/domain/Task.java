package com.taskapi.domain;

import com.taskapi.domain.visitor.ITaskVisitor;
import java.util.List;
import java.util.UUID;

public final class Task {
    private final TaskIdentity identity;
    private final TaskState state;

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

    public Task start() {
        return new Task(identity, state.start());
    }

    public Task complete() {
        return new Task(identity, state.complete());
    }

    public void filter(List<Task> target, TaskStatus expected) {
        state.accept((description, status) -> {
            if (status == expected) target.add(this);
        });
    }

    public void accept(ITaskVisitor visitor) {
        visitor.visit(identity, state);
    }
}