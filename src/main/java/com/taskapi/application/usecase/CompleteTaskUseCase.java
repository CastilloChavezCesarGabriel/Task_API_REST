package com.taskapi.application.usecase;

import com.taskapi.domain.ITaskTransformation;
import com.taskapi.domain.Task;

public final class CompleteTaskUseCase implements ITaskTransformation {
    @Override
    public Task transform(Task task) {
        return task.complete();
    }
}