package com.taskapi.application.usecase;

import com.taskapi.application.result.TaskResult;
import com.taskapi.domain.visitor.ITaskRepository;
import com.taskapi.domain.Task;

public final class StartTaskUseCase {
    private final ITaskRepository repository;

    public StartTaskUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public TaskResult start(String identifier) {
        Task task = repository.find(identifier);
        if (task == null) {
            return TaskResult.reject("Task not found");
        }
        Task started = task.start();
        repository.store(started);
        return TaskResult.accept(started);
    }
}
