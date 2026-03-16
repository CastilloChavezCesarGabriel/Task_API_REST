package com.taskapi.domain;

import com.taskapi.domain.visitor.ITaskVisitor;

public interface ITaskRepository {
    void store(Task task);
    Task remove(String identifier);
    Task apply(String identifier, ITaskTransformation transformation);
    void list(ITaskVisitor visitor);
    void filter(TaskStatus status, ITaskVisitor visitor);
}