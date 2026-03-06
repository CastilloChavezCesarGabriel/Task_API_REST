package com.taskapi.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskapi.application.TaskOperations;
import com.taskapi.application.result.TaskResult;

public final class TaskRequest {
    private final String title;
    private final String description;

    @JsonCreator
    public TaskRequest(@JsonProperty("title") String title, @JsonProperty("description") String description) {
        this.title = title;
        this.description = description;
    }

    public TaskResult create(TaskOperations operations) {
        return operations.create(title, description);
    }
}