package com.taskapi.domain;

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

    public TaskState transition(TaskStatus newStatus) {
        return new TaskState(description, newStatus);
    }

    public boolean matches(TaskStatus expected) {
        return status == expected;
    }
}
