package com.taskapi.application.usecase;

import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;

import java.util.List;

public final class ListTasksUseCase {
    private final ITaskRepository repository;

    public ListTasksUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> list() {
        return repository.collect();
    }

    public List<Task> list(TaskStatus status) {
        return repository.collect(status);
    }
}
