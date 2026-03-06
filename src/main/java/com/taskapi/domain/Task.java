package com.taskapi.domain;

import com.taskapi.domain.visitor.ITaskVisitor;
import java.util.List;
import java.util.UUID;

public final class Task {
    private final TaskIdentity identity;
    private final String description;
    private final TaskStatus status;

    private Task(TaskIdentity identity, String description) {
        this.identity = identity;
        this.description = description;
        this.status = TaskStatus.PENDING;
    }

    private Task(Task origin, TaskStatus newStatus) {
        this.identity = origin.identity;
        this.description = origin.description;
        this.status = newStatus;
    }

    public static Task create(String title, String description) {
        String identifier = UUID.randomUUID().toString();
        TaskIdentity identity = new TaskIdentity(identifier, title);
        return new Task(identity, description);
    }

    public Task start() {
        return new Task(this, TaskStatus.IN_PROGRESS);
    }

    public Task complete() {
        return new Task(this, TaskStatus.COMPLETED);
    }

    public void filter(List<Task> target, TaskStatus expected) {
        if (status == expected) {
            target.add(this);
        }
    }

    public void accept(ITaskVisitor visitor) {
        visitor.visit(identity, new TaskState(description, status));
    }
}