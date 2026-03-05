package com.taskapi.application.usecase;

import com.taskapi.application.result.TaskResult;
import com.taskapi.application.result.ITaskResultConsumer;
import com.taskapi.domain.*;

import com.taskapi.domain.visitor.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class CreateTaskUseCaseTest {
    private TestTaskRepository repository;
    private CreateTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new TestTaskRepository();
        useCase = new CreateTaskUseCase(repository);
    }

    @Test
    void createWithValidTitleAccepts() {
        TaskResult result = useCase.create("Learn REST", "Build API");

        boolean accepted = result.provide(new ITaskResultConsumer<>() {
            @Override
            public Boolean accept(Task task) {
                return true;
            }

            @Override
            public Boolean reject(String reason) {
                return false;
            }
        });

        assertTrue(accepted);
    }

    @Test
    void createStoresTaskInRepository() {
        useCase.create("Learn REST", "Build API");
        assertEquals(1, repository.storedTasks.size());
    }

    @Test
    void createWithBlankTitleRejects() {
        TaskResult result = useCase.create("", "Build API");

        String rejectedReason = result.provide(new ITaskResultConsumer<>() {
            @Override
            public String accept(Task task) {
                fail("Should not accept");
                return null;
            }

            @Override
            public String reject(String reason) {
                return reason;
            }
        });

        assertNotNull(rejectedReason);
    }

    @Test
    void createWithNullTitleRejects() {
        TaskResult result = useCase.create(null, "Build API");

        boolean rejected = result.provide(new ITaskResultConsumer<>() {
            @Override
            public Boolean accept(Task task) {
                return false;
            }

            @Override
            public Boolean reject(String reason) {
                return true;
            }
        });

        assertTrue(rejected);
    }

    private static final class TestTaskRepository implements ITaskRepository {
        final List<Task> storedTasks = new ArrayList<>();

        @Override
        public void store(Task task) {
            storedTasks.add(task);
        }

        @Override
        public void remove(String identifier) {}

        @Override
        public Task find(String identifier) {
            return null;
        }

        @Override
        public List<Task> collect() {
            return List.of();
        }

        @Override
        public List<Task> collect(TaskStatus status) {
            return List.of();
        }
    }
}
