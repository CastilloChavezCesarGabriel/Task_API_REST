package com.taskapi.infrastructure.persistence;

import com.taskapi.domain.ITaskIdentityVisitor;
import com.taskapi.domain.ITaskVisitor;
import com.taskapi.domain.TaskIdentity;
import com.taskapi.domain.TaskState;

public final class IdentifierExtractor implements ITaskVisitor, ITaskIdentityVisitor {
    private String identifier;

    @Override
    public void visit(TaskIdentity identity, TaskState state) {
        identity.accept(this);
    }

    @Override
    public void visit(String identifier, String title) {
        this.identifier = identifier;
    }

    public String extractedIdentifier() {
        return identifier;
    }
}
