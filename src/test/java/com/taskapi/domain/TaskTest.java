package com.taskapi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class TaskTest {

    @Test
    void createAssignsPendingStatus() {
        Task task = Task.create("Learn REST", "Build first API");
        assertStatus(task, TaskStatus.PENDING);
    }

    @Test
    void startChangesStatusToInProgress() {
        Task task = Task.create("Learn REST", "Build first API");
        Task started = task.start();
        assertStatus(started, TaskStatus.IN_PROGRESS);
    }

    @Test
    void startNoLongerMatchesPending() {
        Task task = Task.create("Learn REST", "Build first API");
        Task started = task.start();
        assertNotStatus(started);
    }

    @Test
    void completeChangesStatusToCompleted() {
        Task task = Task.create("Learn REST", "Build first API");
        Task completed = task.complete();
        assertStatus(completed, TaskStatus.COMPLETED);
    }

    @Test
    void completeNoLongerMatchesPending() {
        Task task = Task.create("Learn REST", "Build first API");
        Task completed = task.complete();
        assertNotStatus(completed);
    }

    @Test
    void acceptPushesDataToVisitor() {
        Task task = Task.create("Learn REST", "Build first API");
        boolean[] visited = {false};

        task.accept((identity, state) -> {
            visited[0] = true;
            assertNotNull(identity);
            assertNotNull(state);
        });

        assertTrue(visited[0]);
    }

    @Test
    void acceptPushesCorrectTitleAndDescription() {
        Task task = Task.create("Learn REST", "Build first API");
        String[] captured = new String[2];

        task.accept((identity, state) -> {
            identity.accept((identifier, title) -> captured[0] = title);
            state.accept((description, status) -> captured[1] = description);
        });

        assertEquals("Learn REST", captured[0]);
        assertEquals("Build first API", captured[1]);
    }

    private void assertStatus(Task task, TaskStatus expected) {
        task.accept((identity, state) ->
                state.accept((description, status) ->
                        Assertions.assertEquals(expected, status)));
    }

    private void assertNotStatus(Task task) {
        task.accept((identity, state) ->
                state.accept((description, status) ->
                        Assertions.assertNotEquals(TaskStatus.PENDING, status)));
    }
}