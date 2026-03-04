package com.taskapi.application.usecase;

import com.taskapi.application.result.CreateTaskResult;
import com.taskapi.application.result.ICreateResultConsumer;
import com.taskapi.domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        CreateTaskResult result = useCase.create("Learn REST", "Build API");
        boolean[] accepted = {false};

        result.provide(new ICreateResultConsumer() {
            @Override
            public void accept(Task createdTask) {
                accepted[0] = true;
            }

            @Override
            public void reject(String reason) {
                fail("Should not reject");
            }
        });

        assertTrue(accepted[0]);
    }

    @Test
    void createStoresTaskInRepository() {
        useCase.create("Learn REST", "Build API");
        assertEquals(1, repository.storedTasks.size());
    }

    @Test
    void createWithBlankTitleRejects() {
        CreateTaskResult result = useCase.create("", "Build API");
        String[] rejectedReason = {null};

        result.provide(new ICreateResultConsumer() {
            @Override
            public void accept(Task createdTask) {
                fail("Should not accept");
            }

            @Override
            public void reject(String reason) {
                rejectedReason[0] = reason;
            }
        });

        assertNotNull(rejectedReason[0]);
    }

    @Test
    void createWithNullTitleRejects() {
        CreateTaskResult result = useCase.create(null, "Build API");
        boolean[] rejected = {false};

        result.provide(new ICreateResultConsumer() {
            @Override
            public void accept(Task createdTask) {
                fail("Should not accept");
            }

            @Override
            public void reject(String reason) {
                rejected[0] = true;
            }
        });

        assertTrue(rejected[0]);
    }

    private static final class TestTaskRepository implements ITaskRepository {
        final List<Task> storedTasks = new ArrayList<>();
        final Map<String, Task> storage = new HashMap<>();

        @Override
        public void store(Task task) {
            storedTasks.add(task);
        }

        @Override
        public void remove(String identifier) {
            storage.remove(identifier);
        }

        @Override
        public Task find(String identifier) {
            return storage.get(identifier);
        }

        @Override
        public List<Task> collect() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public List<Task> collect(TaskStatus status) {
            List<Task> filtered = new ArrayList<>();
            for (Task task : storage.values()) {
                if (task.matches(status)) {
                    filtered.add(task);
                }
            }
            return filtered;
        }
    }
}
