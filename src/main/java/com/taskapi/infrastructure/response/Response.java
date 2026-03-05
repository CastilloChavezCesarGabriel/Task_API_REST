package com.taskapi.infrastructure.response;

import com.taskapi.application.result.ITaskResultConsumer;
import com.taskapi.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class Response extends TaskMapper
        implements ITaskResultConsumer<ResponseEntity<Map<String, Object>>> {
    private final HttpStatus successStatus;
    private final HttpStatus failureStatus;

    public Response(HttpStatus successStatus, HttpStatus failureStatus) {
        this.successStatus = successStatus;
        this.failureStatus = failureStatus;
    }

    @Override
    public ResponseEntity<Map<String, Object>> accept(Task task) {
        task.accept(this);
        return ResponseEntity.status(successStatus).body(content);
    }

    @Override
    public ResponseEntity<Map<String, Object>> reject(String reason) {
        return ResponseEntity.status(failureStatus).body(Map.of("error", reason));
    }
}
