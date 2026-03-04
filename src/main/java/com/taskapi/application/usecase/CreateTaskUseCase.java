package com.taskapi.application.usecase;

import com.taskapi.application.result.CreateTaskResult;
import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;

public final class CreateTaskUseCase {
    private final ITaskRepository repository;

    public CreateTaskUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public CreateTaskResult create(String title, String description) {
        if (title == null || title.isBlank()) {
            return CreateTaskResult.reject("Title cannot be empty");
        }
        Task task = Task.create(title, description);
        repository.store(task);
        return CreateTaskResult.accept(task);
    }
}