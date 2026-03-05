package com.taskapi.domain;

import com.taskapi.domain.visitor.ITaskStateVisitor;

public final class TaskState {
    private final String description;
    private final TaskStatus status;

    public TaskState(String description, TaskStatus status) {
        this.description = description;
        this.status = status;
    }

    public void accept(ITaskStateVisitor visitor) {
        visitor.visit(description, status);
    }
}