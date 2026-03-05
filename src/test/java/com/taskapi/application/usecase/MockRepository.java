package com.taskapi.application.usecase;

import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import com.taskapi.domain.visitor.ITaskRepository;

import java.util.ArrayList;
import java.util.List;

public final class MockRepository implements ITaskRepository {
    final List<Task> storedTasks = new ArrayList<>();

    @Override
    public void store(Task task) {
        storedTasks.add(task);
    }

    @Override
    public void remove(String identifier) {}

    @Override
    public Task find(String identifier) {
        return null;
    }

    @Override
    public List<Task> collect() {
        return List.of();
    }

    @Override
    public List<Task> collect(TaskStatus status) {
        return List.of();
    }
}
