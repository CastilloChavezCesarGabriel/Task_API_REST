package com.taskapi.presentation;

import com.taskapi.domain.TaskIdentity;
import com.taskapi.domain.TaskState;
import com.taskapi.domain.TaskStatus;
import com.taskapi.domain.visitor.ITaskIdentityVisitor;
import com.taskapi.domain.visitor.ITaskStateVisitor;
import com.taskapi.domain.visitor.ITaskVisitor;
import java.util.Map;

public final class TaskMapping implements ITaskVisitor, ITaskIdentityVisitor, ITaskStateVisitor {
    private final Map<String, Object> content;

    public TaskMapping(Map<String, Object> content) {
        this.content = content;
    }

    @Override
    public void visit(TaskIdentity identity, TaskState state) {
        identity.accept(this);
        state.accept(this);
    }

    @Override
    public void visit(String identifier, String title) {
        content.put("identifier", identifier);
        content.put("title", title);
    }

    @Override
    public void visit(String description, TaskStatus taskStatus) {
        content.put("description", description);
        content.put("status", taskStatus.name());
    }
}