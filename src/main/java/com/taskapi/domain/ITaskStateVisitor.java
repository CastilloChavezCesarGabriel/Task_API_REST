package com.taskapi.domain;

public interface ITaskStateVisitor {
    void visit(String description, TaskStatus status);
}
