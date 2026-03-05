package com.taskapi.domain;

import com.taskapi.domain.visitor.ITaskIdentityVisitor;

public final class TaskIdentity {
    private final String identifier;
    private final String title;

    public TaskIdentity(String identifier, String title) {
        this.identifier = identifier;
        this.title = title;
    }

    public void accept(ITaskIdentityVisitor visitor) {
        visitor.visit(identifier, title);
    }
}