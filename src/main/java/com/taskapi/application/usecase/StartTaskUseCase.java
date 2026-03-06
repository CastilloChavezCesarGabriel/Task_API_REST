package com.taskapi.application.usecase;

import com.taskapi.application.result.TaskResult;
import com.taskapi.domain.Task;
import com.taskapi.domain.visitor.ITaskRepository;

public final class StartTaskUseCase extends TaskTransitionUseCase {
    public StartTaskUseCase(ITaskRepository repository) {
        super(repository);
    }

    public TaskResult start(String identifier) {
        return apply(identifier);
    }

    @Override
    protected Task transform(Task task) {
        return task.start();
    }
}