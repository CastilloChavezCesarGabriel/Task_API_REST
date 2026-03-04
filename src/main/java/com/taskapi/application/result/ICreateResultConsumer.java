package com.taskapi.application.result;

import com.taskapi.domain.Task;

public interface ICreateResultConsumer {
    void accept(Task createdTask);
    void reject(String reason);
}