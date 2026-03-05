package com.taskapi.application.usecase;

import com.taskapi.application.result.TaskResult;
import com.taskapi.domain.Task;
import com.taskapi.domain.visitor.ITaskRepository;

public abstract class TaskTransitionUseCase {
    protected final ITaskRepository repository;

    protected TaskTransitionUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    protected TaskResult apply(String identifier) {
        Task task = repository.find(identifier);
        if (task == null) {
            return TaskResult.reject("Task not found");
        }
        Task transitioned = transform(task);
        repository.store(transitioned);
        return TaskResult.accept(transitioned);
    }

    protected abstract Task transform(Task task);
}
