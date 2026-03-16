package com.taskapi.presentation;

import com.taskapi.application.TaskFacade;
import com.taskapi.domain.Task;
import com.taskapi.domain.TaskStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public final class TaskController {
    private final TaskFacade taskFacade;

    public TaskController(TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody TaskRequest request) {
        Task task = request.create(this.taskFacade);
        if (task == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Title cannot be empty"));
        }
        return respond(task, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> list(@RequestParam(required = false) String status) {
        List<Map<String, Object>> items = new ArrayList<>();
        resolve(status, new TaskCollector(items));
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{identifier}/start")
    public ResponseEntity<Map<String, Object>> start(@PathVariable String identifier) {
        return handle(taskFacade.start(identifier));
    }

    @PutMapping("/{identifier}/complete")
    public ResponseEntity<Map<String, Object>> complete(@PathVariable String identifier) {
        return handle(taskFacade.complete(identifier));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable String identifier) {
        return handle(taskFacade.remove(identifier));
    }

    private ResponseEntity<Map<String, Object>> handle(Task task) {
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Task not found"));
        }
        return respond(task, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> respond(Task task, HttpStatus status) {
        Map<String, Object> content = new LinkedHashMap<>();
        task.accept(new TaskMapping(content));
        return ResponseEntity.status(status).body(content);
    }

    private void resolve(String status, TaskCollector collector) {
        if (status == null || status.isBlank()) {
            taskFacade.list(collector);
        } else {
            taskFacade.filter(TaskStatus.valueOf(status.toUpperCase()), collector);
        }
    }
}