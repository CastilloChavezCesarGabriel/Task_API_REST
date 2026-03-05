package com.taskapi.domain.visitor;

import com.taskapi.domain.TaskStatus;

public interface ITaskStateVisitor {
    void visit(String description, TaskStatus status);
}