package com.taskapi.application.result;

import com.taskapi.domain.Task;

public interface ITaskResultConsumer<T> {
    T accept(Task task);
    T reject(String reason);
}
