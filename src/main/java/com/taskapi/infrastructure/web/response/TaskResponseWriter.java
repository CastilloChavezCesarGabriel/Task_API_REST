package com.taskapi.infrastructure.web.response;

import com.taskapi.domain.ITaskIdentityVisitor;
import com.taskapi.domain.ITaskStateVisitor;
import com.taskapi.domain.ITaskVisitor;
import com.taskapi.domain.TaskIdentity;
import com.taskapi.domain.TaskState;
import com.taskapi.domain.TaskStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public final class TaskResponseWriter
        implements ITaskVisitor, ITaskIdentityVisitor, ITaskStateVisitor {

    private final Map<String, Object> response = new LinkedHashMap<>();

    @Override
    public void visit(TaskIdentity identity, TaskState state) {
        identity.accept(this);
        state.accept(this);
    }

    @Override
    public void visit(String identifier, String title) {
        response.put("identifier", identifier);
        response.put("title", title);
    }

    @Override
    public void visit(String description, TaskStatus status) {
        response.put("description", description);
        response.put("status", status.name());
    }

    public Map<String, Object> build() {
        return response;
    }
}
