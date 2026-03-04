package com.taskapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class TaskTest {

    @Test
    void createAssignsPendingStatus() {
        Task task = Task.create("Learn REST", "Build first API");
        assertTrue(task.matches(TaskStatus.PENDING));
    }

    @Test
    void completeChangesStatusToCompleted() {
        Task task = Task.create("Learn REST", "Build first API");
        task.complete();
        assertTrue(task.matches(TaskStatus.COMPLETED));
    }

    @Test
    void completeNoLongerMatchesPending() {
        Task task = Task.create("Learn REST", "Build first API");
        task.complete();
        assertFalse(task.matches(TaskStatus.PENDING));
    }

    @Test
    void acceptPushesDataToVisitor() {
        Task task = Task.create("Learn REST", "Build first API");
        boolean[] visited = {false};

        task.accept(new ITaskVisitor() {
            @Override
            public void visit(TaskIdentity identity, TaskState state) {
                visited[0] = true;
                assertNotNull(identity);
                assertNotNull(state);
            }
        });

        assertTrue(visited[0]);
    }

    @Test
    void acceptPushesCorrectTitleAndDescription() {
        Task task = Task.create("Learn REST", "Build first API");
        String[] captured = new String[2];

        task.accept(new ITaskVisitor() {
            @Override
            public void visit(TaskIdentity identity, TaskState state) {
                identity.accept(new ITaskIdentityVisitor() {
                    @Override
                    public void visit(String identifier, String title) {
                        captured[0] = title;
                    }
                });
                state.accept(new ITaskStateVisitor() {
                    @Override
                    public void visit(String description, TaskStatus status) {
                        captured[1] = description;
                    }
                });
            }
        });

        assertEquals("Learn REST", captured[0]);
        assertEquals("Build first API", captured[1]);
    }
}
