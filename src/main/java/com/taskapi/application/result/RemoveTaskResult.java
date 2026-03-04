package com.taskapi.application.result;

public final class RemoveTaskResult {
    private final boolean successful;
    private final String reason;

    private RemoveTaskResult(boolean successful, String reason) {
        this.successful = successful;
        this.reason = reason;
    }

    public static RemoveTaskResult accept() {
        return new RemoveTaskResult(true, null);
    }

    public static RemoveTaskResult reject(String reason) {
        return new RemoveTaskResult(false, reason);
    }

    public void provide(IRemoveResultConsumer consumer) {
        if (successful) {
            consumer.accept();
        } else {
            consumer.reject(reason);
        }
    }
}