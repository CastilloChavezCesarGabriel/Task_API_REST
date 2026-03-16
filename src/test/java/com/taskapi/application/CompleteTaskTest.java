package com.taskapi.application;

import com.taskapi.application.usecase.CompleteTaskUseCase;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public final class CompleteTaskTest {
    private final CompleteTaskUseCase useCase = new CompleteTaskUseCase();

    @Test
    void testTransformChangesStatus() {
        Task task = Task.create("Learn REST", "Build API");
        Task completed = useCase.transform(task);

        List<Task> matching = new ArrayList<>();
        completed.filter(matching, TaskStatus.COMPLETED);
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
