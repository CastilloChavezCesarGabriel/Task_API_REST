package com.taskapi.infrastructure.persistence;

import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public final class InMemoryTaskRepository implements ITaskRepository {
    private final Map<String, Task> storage = new ConcurrentHashMap<>();

    @Override
    public void store(Task task) {
        IdentifierExtractor extractor = new IdentifierExtractor();
        task.accept(extractor);
        storage.put(extractor.extractedIdentifier(), task);
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
            if (task.matches(status)) {
                filtered.add(task);
            }
        }
        return filtered;
    }
}
