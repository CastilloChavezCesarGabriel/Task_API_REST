package com.taskapi.application.usecase;

import com.taskapi.application.result.TaskResult;
import com.taskapi.domain.Task;
import com.taskapi.domain.visitor.ITaskRepository;

public final class CompleteTaskUseCase extends TaskTransitionUseCase {
    public CompleteTaskUseCase(ITaskRepository repository) {
        super(repository);
    }

    public TaskResult complete(String identifier) {
        return apply(identifier);
    }

    @Override
    protected Task transform(Task task) {
        return task.complete();
    }
}