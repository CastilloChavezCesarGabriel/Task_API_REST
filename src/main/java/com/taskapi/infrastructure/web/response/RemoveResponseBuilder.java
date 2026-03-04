package com.taskapi.infrastructure.web.response;

import com.taskapi.application.result.IRemoveResultConsumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class RemoveResponseBuilder implements IRemoveResultConsumer {
    private ResponseEntity<Map<String, Object>> response;

    @Override
    public void accept() {
        response = ResponseEntity.ok(Map.of("message", "Task removed successfully"));
    }

    @Override
    public void reject(String reason) {
        response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", reason));
    }

    public ResponseEntity<Map<String, Object>> build() {
        return response;
    }
}
