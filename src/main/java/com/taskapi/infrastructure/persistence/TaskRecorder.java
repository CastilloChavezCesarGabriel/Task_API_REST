package com.taskapi.infrastructure.persistence;

import com.taskapi.domain.visitor.ITaskIdentityVisitor;
import com.taskapi.domain.visitor.ITaskVisitor;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskIdentity;
import com.taskapi.domain.TaskState;

import java.util.Map;

public final class TaskRecorder implements ITaskVisitor, ITaskIdentityVisitor {
    private final Map<String, Task> storage;
    private final Task task;

    public TaskRecorder(Map<String, Task> storage, Task task) {
        this.storage = storage;
        this.task = task;
    }

    @Override
    public void visit(TaskIdentity identity, TaskState state) {
        identity.accept(this);
    }

    @Override
    public void visit(String identifier, String title) {
        storage.put(identifier, task);
    }
}