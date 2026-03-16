package com.taskapi.application;

import com.taskapi.application.usecase.CompleteTaskUseCase;
import com.taskapi.application.usecase.CreateTaskUseCase;
import com.taskapi.application.usecase.RemoveTaskUseCase;
import com.taskapi.application.usecase.StartTaskUseCase;
import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import com.taskapi.domain.visitor.ITaskVisitor;

public final class TaskFacade {
    private final ITaskRepository repository;
    private final CreateTaskUseCase createTask;
    private final StartTaskUseCase startTask;
    private final CompleteTaskUseCase completeTask;
    private final RemoveTaskUseCase removeTask;

    public TaskFacade(ITaskRepository repository) {
        this.repository = repository;
        this.createTask = new CreateTaskUseCase(repository);
        this.startTask = new StartTaskUseCase();
        this.completeTask = new CompleteTaskUseCase();
        this.removeTask = new RemoveTaskUseCase(repository);
    }

    public Task create(String title, String description) {
        return createTask.create(title, description);
    }

    public Task start(String identifier) {
        return repository.apply(identifier, startTask);
    }

    public Task complete(String identifier) {
        return repository.apply(identifier, completeTask);
    }

    public Task remove(String identifier) {
        return removeTask.remove(identifier);
    }

    public void list(ITaskVisitor visitor) {
        repository.list(visitor);
    }

    public void filter(TaskStatus status, ITaskVisitor visitor) {
        repository.filter(status, visitor);
    }
}