package com.taskapi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public final class TaskTest {
    @Test
    void createAssignsPendingStatus() {
        Task task = Task.create("Learn REST", "Build first API");
        assertStatus(task, TaskStatus.PENDING);
    }

    @Test
    void startPreservesOriginalStatus() {
        Task original = Task.create("Learn REST", "Build first API");
        Task started = original.start();
        assertStatus(started, TaskStatus.IN_PROGRESS);
        assertStatus(original, TaskStatus.PENDING);
    }

    @Test
    void completePreservesOriginalStatus() {
        Task original = Task.create("Learn REST", "Build first API");
        Task completed = original.complete();
        assertStatus(completed, TaskStatus.COMPLETED);
        assertStatus(original, TaskStatus.PENDING);
    }

    @Test
    void acceptPushesCorrectTitleAndDescription() {
        Task task = Task.create("Learn REST", "Build first API");
        Snapshot captor = new Snapshot();
        task.accept(captor);
        captor.assertTitle("Learn REST");
        captor.assertDescription("Build first API");
    }

    @Test
    void filterCollectsMatchingTask() {
        Task task = Task.create("Learn REST", "Build first API");
        List<Task> target = new ArrayList<>();
        task.filter(target, TaskStatus.PENDING);
        Assertions.assertEquals(1, target.size());
    }

    @Test
    void filterIgnoresNonMatchingTask() {
        Task task = Task.create("Learn REST", "Build first API");
        List<Task> target = new ArrayList<>();
        task.filter(target, TaskStatus.COMPLETED);
        Assertions.assertTrue(target.isEmpty());
    }

    private void assertStatus(Task task, TaskStatus expected) {
        task.accept((identity, state) ->
                state.accept((description, status) ->
                        Assertions.assertEquals(expected, status)));
    }
}
