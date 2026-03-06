package com.taskapi.application.usecase;

import com.taskapi.application.result.TaskResult;
import com.taskapi.domain.visitor.ITaskRepository;
import com.taskapi.domain.Task;

public final class CreateTaskUseCase {
    private final ITaskRepository repository;

    public CreateTaskUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public TaskResult create(String title, String description) {
        if (title == null || title.isBlank()) {
            return TaskResult.reject("Title cannot be empty");
        }
        Task task = Task.create(title, description);
        repository.store(task);
        return TaskResult.accept(task);
    }
}