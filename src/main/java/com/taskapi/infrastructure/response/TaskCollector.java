package com.taskapi.infrastructure.response;

import com.taskapi.domain.TaskIdentity;
import com.taskapi.domain.TaskState;
import java.util.List;
import java.util.Map;

public final class TaskCollector extends TaskMapper {
    private final List<Map<String, Object>> target;

    public TaskCollector(List<Map<String, Object>> target) {
        this.target = target;
    }

    @Override
    public void visit(TaskIdentity identity, TaskState state) {
        super.visit(identity, state);
        target.add(content);
    }
}