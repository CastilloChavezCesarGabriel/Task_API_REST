package com.taskapi.application.usecase;

import com.taskapi.domain.visitor.ITaskRepository;
import com.taskapi.domain.visitor.ITaskVisitor;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;

public final class ListTasksUseCase {
    private final ITaskRepository repository;

    public ListTasksUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public void list(ITaskVisitor visitor) {
        for (Task task : repository.collect()) {
            task.accept(visitor);
        }
    }

    public void list(TaskStatus status, ITaskVisitor visitor) {
        for (Task task : repository.collect(status)) {
            task.accept(visitor);
        }
    }
}