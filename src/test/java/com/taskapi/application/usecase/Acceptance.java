package com.taskapi.application.usecase;

import com.taskapi.application.result.ITaskResultConsumer;
import com.taskapi.domain.Task;

public final class Acceptance implements ITaskResultConsumer<Boolean> {

    @Override
    public Boolean accept(Task task) {
        return true;
    }

    @Override
    public Boolean reject(String reason) {
        return false;
    }
}
