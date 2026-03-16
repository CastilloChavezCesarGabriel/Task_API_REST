package com.taskapi.application.usecase;

import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;

public final class RemoveTaskUseCase {
    private final ITaskRepository repository;

    public RemoveTaskUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public Task remove(String identifier) {
        return repository.remove(identifier);
    }
}