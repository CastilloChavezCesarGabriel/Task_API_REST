package com.taskapi.application.result;

import com.taskapi.domain.Task;

public final class TaskResult {
    private final Task task;
    private final String reason;

    private TaskResult(Task task, String reason) {
        this.task = task;
        this.reason = reason;
    }

    public static TaskResult accept(Task task) {
        return new TaskResult(task, null);
    }

    public static TaskResult reject(String reason) {
        return new TaskResult(null, reason);
    }

    public <T> T provide(ITaskResultConsumer<T> consumer) {
        if (task != null) {
            return consumer.accept(task);
        }
        return consumer.reject(reason);
    }
}