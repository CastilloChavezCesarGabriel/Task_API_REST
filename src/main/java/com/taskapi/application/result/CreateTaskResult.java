package com.taskapi.application.result;

import com.taskapi.domain.Task;

public final class CreateTaskResult {
    private final Task createdTask;
    private final String reason;

    private CreateTaskResult(Task createdTask, String reason) {
        this.createdTask = createdTask;
        this.reason = reason;
    }

    public static CreateTaskResult accept(Task createdTask) {
        return new CreateTaskResult(createdTask, null);
    }

    public static CreateTaskResult reject(String reason) {
        return new CreateTaskResult(null, reason);
    }

    public void provide(ICreateResultConsumer consumer) {
        if (createdTask != null) {
            consumer.accept(createdTask);
        } else {
            consumer.reject(reason);
        }
    }
}