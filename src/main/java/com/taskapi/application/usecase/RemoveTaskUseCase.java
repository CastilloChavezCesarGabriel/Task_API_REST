package com.taskapi.application.usecase;

import com.taskapi.application.result.TaskResult;
import com.taskapi.domain.visitor.ITaskRepository;
import com.taskapi.domain.Task;

public final class RemoveTaskUseCase {
    private final ITaskRepository repository;

    public RemoveTaskUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public TaskResult remove(String identifier) {
        Task task = repository.find(identifier);
        if (task == null) {
            return TaskResult.reject("Task not found");
        }
        repository.remove(identifier);
        return TaskResult.accept(task);
    }
}