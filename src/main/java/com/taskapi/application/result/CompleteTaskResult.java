package com.taskapi.application.result;

import com.taskapi.domain.Task;

public final class CompleteTaskResult {
    private final Task completedTask;
    private final String reason;

    private CompleteTaskResult(Task completedTask, String reason) {
        this.completedTask = completedTask;
        this.reason = reason;
    }

    public static CompleteTaskResult accept(Task completedTask) {
        return new CompleteTaskResult(completedTask, null);
    }

    public static CompleteTaskResult reject(String reason) {
        return new CompleteTaskResult(null, reason);
    }

    public void provide(ICompleteResultConsumer consumer) {
        if (completedTask != null) {
            consumer.accept(completedTask);
        } else {
            consumer.reject(reason);
        }
    }
}