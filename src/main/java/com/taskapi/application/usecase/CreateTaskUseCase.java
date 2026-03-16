package com.taskapi.application.usecase;

import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;

public final class CreateTaskUseCase {
    private final ITaskRepository repository;

    public CreateTaskUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public Task create(String title, String description) {
        if (title == null || title.isBlank()) return null;
        Task task = Task.create(title, description);
        repository.store(task);
        return task;
    }
}