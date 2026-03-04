package com.taskapi.domain;

public interface ITaskVisitor {
    void visit(TaskIdentity identity, TaskState state);
}
