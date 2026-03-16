package com.taskapi.application;

import com.taskapi.application.usecase.StartTaskUseCase;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public final class StartTaskTest {
    private final StartTaskUseCase useCase = new StartTaskUseCase();

    @Test
    void testTransformChangesStatus() {
        Task task = Task.create("Learn REST", "Build API");
        Task started = useCase.transform(task);

        List<Task> matching = new ArrayList<>();
        started.filter(matching, TaskStatus.IN_PROGRESS);
        assertEquals(1, matching.size());
    }

    @Test
    void testTransformPreservesOriginal() {
        Task task = Task.create("Learn REST", "Build API");
        useCase.transform(task);

        List<Task> pending = new ArrayList<>();
        task.filter(pending, TaskStatus.PENDING);
        assertEquals(1, pending.size());
    }
}
