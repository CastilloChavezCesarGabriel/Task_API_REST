package com.taskapi.application;

import com.taskapi.application.result.CompleteTaskResult;
import com.taskapi.application.result.CreateTaskResult;
import com.taskapi.application.result.RemoveTaskResult;
import com.taskapi.application.usecase.CompleteTaskUseCase;
import com.taskapi.application.usecase.CreateTaskUseCase;
import com.taskapi.application.usecase.ListTasksUseCase;
import com.taskapi.application.usecase.RemoveTaskUseCase;
import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class TaskOperations {
    private final CreateTaskUseCase createUseCase;
    private final CompleteTaskUseCase completeUseCase;
    private final RemoveTaskUseCase removeUseCase;
    private final ListTasksUseCase listUseCase;

    public TaskOperations(ITaskRepository repository) {
        this.createUseCase = new CreateTaskUseCase(repository);
        this.completeUseCase = new CompleteTaskUseCase(repository);
        this.removeUseCase = new RemoveTaskUseCase(repository);
        this.listUseCase = new ListTasksUseCase(repository);
    }

    public CreateTaskResult create(String title, String description) {
        return createUseCase.create(title, description);
    }

    public CompleteTaskResult complete(String identifier) {
        return completeUseCase.complete(identifier);
    }

    public RemoveTaskResult remove(String identifier) {
        return removeUseCase.remove(identifier);
    }

    public List<Task> list() {
        return listUseCase.list();
    }

    public List<Task> list(TaskStatus status) {
        return listUseCase.list(status);
    }
}