package com.taskapi.infrastructure;

import com.taskapi.application.TaskOperations;
import com.taskapi.application.result.TaskResult;
import com.taskapi.domain.TaskStatus;
import com.taskapi.infrastructure.request.TaskRequest;
import com.taskapi.infrastructure.response.Response;
import com.taskapi.infrastructure.response.TaskCollector;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Map<String, Object>> create(@RequestBody TaskRequest request) {
        TaskResult result = operations.create(request.getTitle(), request.getDescription());
        return result.provide(new Response(HttpStatus.CREATED, HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> list(@RequestParam(required = false) String status) {
        List<Map<String, Object>> items = new ArrayList<>();
        resolve(status, new TaskCollector(items));
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{identifier}/start")
    public ResponseEntity<Map<String, Object>> start(@PathVariable String identifier) {
        return handle(operations.start(identifier));
    }

    @PutMapping("/{identifier}/complete")
    public ResponseEntity<Map<String, Object>> complete(@PathVariable String identifier) {
        return handle(operations.complete(identifier));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable String identifier) {
        return handle(operations.remove(identifier));
    }

    private ResponseEntity<Map<String, Object>> handle(TaskResult result) {
        return result.provide(new Response(HttpStatus.OK, HttpStatus.NOT_FOUND));
    }

    private void resolve(String status, TaskCollector collector) {
        if (status == null || status.isBlank()) {
            operations.list(collector);
        } else {
            operations.list(TaskStatus.valueOf(status.toUpperCase()), collector);
        }
    }
}