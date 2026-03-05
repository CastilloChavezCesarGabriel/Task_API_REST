package com.taskapi.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.taskapi.domain.visitor.ITaskRepository;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import org.springframework.stereotype.Repository;

@Repository
public final class TaskRepository implements ITaskRepository {
    private final Map<String, Task> storage = new ConcurrentHashMap<>();

    @Override
    public void store(Task task) {
        task.accept(new TaskRecorder(storage, task));
    }

    @Override
    public void remove(String identifier) {
        storage.remove(identifier);
    }

    @Override
    public Task find(String identifier) {
        return storage.get(identifier);
    }

    @Override
    public List<Task> collect() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Task> collect(TaskStatus status) {
        List<Task> filtered = new ArrayList<>();
        for (Task task : storage.values()) {
            task.filter(filtered, status);
        }
        return filtered;
    }
}