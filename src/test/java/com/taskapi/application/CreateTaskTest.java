package com.taskapi.application;

import com.taskapi.application.usecase.CreateTaskUseCase;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import com.taskapi.infrastructure.persistence.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public final class CreateTaskTest {
    private CreateTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateTaskUseCase(new TaskRepository());
    }

    @Test
    void testCreateValidTask() {
        Task task = useCase.create("Learn REST", "Build first API");

        List<Task> pending = new ArrayList<>();
        task.filter(pending, TaskStatus.PENDING);

        assertAll(
            () -> assertNotNull(task),
            () -> assertEquals(1, pending.size())
        );
    }

    @Test
    void testCreateBlankTitle() {
        assertNull(useCase.create("", "Build API"));
    }

    @Test
    void testCreateNullTitle() {
        assertNull(useCase.create(null, "Build API"));
    }

    @Test
    void testCreateWhitespaceTitle() {
        assertNull(useCase.create("   ", "Build API"));
    }

    @Test
    void testFilterIgnoresNonMatchingStatus() {
        Task task = useCase.create("Learn REST", "Build first API");

        List<Task> completed = new ArrayList<>();
        task.filter(completed, TaskStatus.COMPLETED);
        assertTrue(completed.isEmpty());
    }
}
