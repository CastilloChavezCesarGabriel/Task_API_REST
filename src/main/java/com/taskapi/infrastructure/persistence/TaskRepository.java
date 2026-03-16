package com.taskapi.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.taskapi.domain.ITaskRepository;
import com.taskapi.domain.ITaskTransformation;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import com.taskapi.domain.visitor.ITaskVisitor;
import org.springframework.stereotype.Repository;

@Repository
public final class TaskRepository implements ITaskRepository {
    private final Map<String, Task> storage = new ConcurrentHashMap<>();

    @Override
    public void store(Task task) {
        task.accept(new TaskRecorder(storage, task));
    }

    @Override
    public Task remove(String identifier) {
        return storage.remove(identifier);
    }

    @Override
    public Task apply(String identifier, ITaskTransformation transformation) {
        Task task = storage.get(identifier);
        if (task == null) return null;
        Task transformed = transformation.transform(task);
        store(transformed);
        return transformed;
    }

    @Override
    public void list(ITaskVisitor visitor) {
        for (Task task : storage.values()) {
            task.accept(visitor);
        }
    }

    @Override
    public void filter(TaskStatus status, ITaskVisitor visitor) {
        List<Task> matching = new ArrayList<>();
        for (Task task : storage.values()) {
            task.filter(matching, status);
        }
        for (Task task : matching) {
            task.accept(visitor);
        }
    }
}