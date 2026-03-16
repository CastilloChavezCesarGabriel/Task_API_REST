package com.taskapi.presentation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskapi.application.TaskFacade;
import com.taskapi.domain.Task;

public final class TaskRequest {
    private final String title;
    private final String description;

    @JsonCreator
    public TaskRequest(@JsonProperty("title") String title, @JsonProperty("description") String description) {
        this.title = title;
        this.description = description;
    }

    public Task create(TaskFacade taskFacade) {
        return taskFacade.create(title, description);
    }
}