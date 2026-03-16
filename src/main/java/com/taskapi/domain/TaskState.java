package com.taskapi.domain;

import com.taskapi.domain.visitor.ITaskStateVisitor;

public final class TaskState {
    private final String description;
    private final TaskStatus status;

    public TaskState(String description, TaskStatus status) {
        this.description = description;
        this.status = status;
    }

    public TaskState start() {
        return new TaskState(description, TaskStatus.IN_PROGRESS);
    }

    public TaskState complete() {
        return new TaskState(description, TaskStatus.COMPLETED);
    }

    public void accept(ITaskStateVisitor visitor) {
        visitor.visit(description, status);
    }
}