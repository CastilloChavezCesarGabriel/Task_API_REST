package com.taskapi.presentation;

import com.taskapi.domain.TaskIdentity;
import com.taskapi.domain.TaskState;
import com.taskapi.domain.visitor.ITaskVisitor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TaskCollector implements ITaskVisitor {
    private final List<Map<String, Object>> target;

    public TaskCollector(List<Map<String, Object>> target) {
        this.target = target;
    }

    @Override
    public void visit(TaskIdentity identity, TaskState state) {
        Map<String, Object> content = new LinkedHashMap<>();
        TaskMapping mapping = new TaskMapping(content);
        identity.accept(mapping);
        state.accept(mapping);
        target.add(content);
    }
}