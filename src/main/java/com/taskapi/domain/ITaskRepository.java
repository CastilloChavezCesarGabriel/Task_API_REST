package com.taskapi.domain;

import java.util.List;

public interface ITaskRepository {
    void store(Task task);
    void remove(String identifier);
    Task find(String identifier);
    List<Task> collect();
    List<Task> collect(TaskStatus status);
}
