package com.taskapi.application.usecase;

import com.taskapi.domain.visitor.ITaskRepository;
import com.taskapi.domain.visitor.ITaskVisitor;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import java.util.List;

public final class ListTasksUseCase {
    private final ITaskRepository repository;

    public ListTasksUseCase(ITaskRepository repository) {
        this.repository = repository;
    }

    public void list(ITaskVisitor visitor) {
        traverse(repository.collect(), visitor);
    }

    public void list(TaskStatus status, ITaskVisitor visitor) {
        traverse(repository.collect(status), visitor);
    }

    private void traverse(List<Task> tasks, ITaskVisitor visitor) {
        for (Task task : tasks) {
            task.accept(visitor);
        }
    }
}