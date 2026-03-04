package com.taskapi.application.usecase;

import com.taskapi.application.result.RemoveTaskResult;
import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;

public final class RemoveTaskUseCase {
    private final ITaskRepository repository;

    public RemoveTaskUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public RemoveTaskResult remove(String identifier) {
        Task task = repository.find(identifier);
        if (task == null) {
            return RemoveTaskResult.reject("Task not found");
        }
        repository.remove(identifier);
        return RemoveTaskResult.accept();
    }
}
