package com.taskapi.application;

import com.taskapi.application.result.TaskResult;
import com.taskapi.application.usecase.CompleteTaskUseCase;
import com.taskapi.application.usecase.CreateTaskUseCase;
import com.taskapi.application.usecase.ListTasksUseCase;
import com.taskapi.application.usecase.RemoveTaskUseCase;
import com.taskapi.application.usecase.StartTaskUseCase;
import com.taskapi.domain.visitor.ITaskRepository;
import com.taskapi.domain.visitor.ITaskVisitor;
import com.taskapi.domain.TaskStatus;
import org.springframework.stereotype.Service;

@Service
public final class TaskOperations {
    private final ITaskRepository repository;

    public TaskOperations(ITaskRepository repository) {
        this.repository = repository;
    }

    public TaskResult create(String title, String description) {
        return new CreateTaskUseCase(repository).create(title, description);
    }

    public TaskResult start(String identifier) {
        return new StartTaskUseCase(repository).start(identifier);
    }

    public TaskResult complete(String identifier) {
        return new CompleteTaskUseCase(repository).complete(identifier);
    }

    public TaskResult remove(String identifier) {
        return new RemoveTaskUseCase(repository).remove(identifier);
    }

    public void list(ITaskVisitor visitor) {
        new ListTasksUseCase(repository).list(visitor);
    }

    public void list(TaskStatus status, ITaskVisitor visitor) {
        new ListTasksUseCase(repository).list(status, visitor);
    }
}