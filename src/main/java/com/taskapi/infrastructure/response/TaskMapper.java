package com.taskapi.infrastructure.response;

import com.taskapi.domain.visitor.ITaskIdentityVisitor;
import com.taskapi.domain.visitor.ITaskStateVisitor;
import com.taskapi.domain.visitor.ITaskVisitor;
import com.taskapi.domain.TaskIdentity;
import com.taskapi.domain.TaskState;
import com.taskapi.domain.TaskStatus;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class TaskMapper implements ITaskVisitor,
        ITaskIdentityVisitor, ITaskStateVisitor {
    protected Map<String, Object> content;

    @Override
    public void visit(TaskIdentity identity, TaskState state) {
        content = new LinkedHashMap<>();
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
