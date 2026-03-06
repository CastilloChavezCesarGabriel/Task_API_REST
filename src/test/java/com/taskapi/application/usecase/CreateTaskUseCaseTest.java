package com.taskapi.application.usecase;

import com.taskapi.application.result.TaskResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public final class CreateTaskUseCaseTest {
    private FakeRepository repository;
    private CreateTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeRepository();
        useCase = new CreateTaskUseCase(repository);
    }

    @Test
    void createWithValidTitleAccepts() {
        TaskResult result = useCase.create("Learn REST", "Build API");
        assertTrue(result.provide(new Acceptance()));
    }

    @Test
    void createStoresTaskInRepository() {
        useCase.create("Learn REST", "Build API");
        assertEquals(1, repository.count());
    }

    @Test
    void createWithBlankTitleRejects() {
        TaskResult result = useCase.create("", "Build API");
        assertFalse(result.provide(new Acceptance()));
    }

    @Test
    void createWithNullTitleRejects() {
        TaskResult result = useCase.create(null, "Build API");
        assertFalse(result.provide(new Acceptance()));
    }
}