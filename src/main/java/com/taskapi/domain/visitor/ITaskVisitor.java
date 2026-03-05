package com.taskapi.domain.visitor;

import com.taskapi.domain.TaskIdentity;
import com.taskapi.domain.TaskState;

public interface ITaskVisitor {
    void visit(TaskIdentity identity, TaskState state);
}