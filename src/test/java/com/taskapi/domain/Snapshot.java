package com.taskapi.domain;

import com.taskapi.domain.visitor.ITaskIdentityVisitor;
import com.taskapi.domain.visitor.ITaskStateVisitor;
import com.taskapi.domain.visitor.ITaskVisitor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class Snapshot implements ITaskVisitor, ITaskIdentityVisitor, ITaskStateVisitor {
    private TaskIdentity identity;
    private String title;
    private String description;

    @Override
    public void visit(TaskIdentity identity, TaskState state) {
        this.identity = identity;
        identity.accept(this);
        state.accept(this);
    }

    @Override
    public void visit(String identifier, String title) {
        this.title = title;
    }

    @Override
    public void visit(String description, TaskStatus status) {
        this.description = description;
    }

    public void assertVisited() {
        assertNotNull(identity);
    }

    public void assertTitle(String expected) {
        assertEquals(expected, title);
    }

    public void assertDescription(String expected) {
        assertEquals(expected, description);
    }
}