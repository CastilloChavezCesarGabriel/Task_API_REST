package com.taskapi.application.result;

import com.taskapi.domain.Task;

public interface ICompleteResultConsumer {
    void accept(Task completedTask);
    void reject(String reason);
}