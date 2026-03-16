package com.taskapi.application;

import com.taskapi.application.usecase.RemoveTaskUseCase;
import com.taskapi.infrastructure.persistence.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public final class RemoveTaskTest {
    private RemoveTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RemoveTaskUseCase(new TaskRepository());
    }

    @Test
    void testRemoveNonExistent() {
        assertNull(useCase.remove("nonexistent"));
    }

    @Test
    void testRemoveEmptyIdentifier() {
        assertNull(useCase.remove(""));
    }
}
