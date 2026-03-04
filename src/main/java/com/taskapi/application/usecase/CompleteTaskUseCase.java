package com.taskapi.application.usecase;

import com.taskapi.application.result.CompleteTaskResult;
import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;

public final class CompleteTaskUseCase {
    private final ITaskRepository repository;

    public CompleteTaskUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public CompleteTaskResult complete(String identifier) {
        Task task = repository.find(identifier);
        if (task == null) {
            return CompleteTaskResult.reject("Task not found");
        }
        task.complete();
        return CompleteTaskResult.accept(task);
    }
}