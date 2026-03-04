package com.taskapi.infrastructure.web;

import com.taskapi.application.TaskOperations;
import com.taskapi.application.result.CompleteTaskResult;
import com.taskapi.application.result.CreateTaskResult;
import com.taskapi.application.result.RemoveTaskResult;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import com.taskapi.infrastructure.web.request.CreateTaskRequest;
import com.taskapi.infrastructure.web.response.CompleteResponseBuilder;
import com.taskapi.infrastructure.web.response.CreateResponseBuilder;
import com.taskapi.infrastructure.web.response.RemoveResponseBuilder;
import com.taskapi.infrastructure.web.response.TaskResponseWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public final class TaskController {
    private final TaskOperations operations;

    public TaskController(TaskOperations operations) {
        this.operations = operations;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody CreateTaskRequest request) {
        CreateTaskResult result = operations.create(request.getTitle(), request.getDescription());
        CreateResponseBuilder builder = new CreateResponseBuilder();
        result.provide(builder);
        return builder.build();
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> list(@RequestParam(required = false) String status) {
        List<Task> tasks = resolve(status);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Task task : tasks) {
            TaskResponseWriter writer = new TaskResponseWriter();
            task.accept(writer);
            response.add(writer.build());
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{identifier}/complete")
    public ResponseEntity<Map<String, Object>> complete(@PathVariable String identifier) {
        CompleteTaskResult result = operations.complete(identifier);
        CompleteResponseBuilder builder = new CompleteResponseBuilder();
        result.provide(builder);
        return builder.build();
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable String identifier) {
        RemoveTaskResult result = operations.remove(identifier);
        RemoveResponseBuilder builder = new RemoveResponseBuilder();
        result.provide(builder);
        return builder.build();
    }

    private List<Task> resolve(String status) {
        if (status == null || status.isBlank()) {
            return operations.list();
        }
        return operations.list(TaskStatus.valueOf(status.toUpperCase()));
    }
}
