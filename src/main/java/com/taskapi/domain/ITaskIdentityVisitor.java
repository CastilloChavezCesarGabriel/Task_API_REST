package com.taskapi.domain;

public interface ITaskIdentityVisitor {
    void visit(String identifier, String title);
}
