package com.taskapi.domain.visitor;

public interface ITaskIdentityVisitor {
    void visit(String identifier, String title);
}