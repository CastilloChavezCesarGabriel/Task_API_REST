package com.taskapi.infrastructure.web.response;

import com.taskapi.application.result.ICompleteResultConsumer;
import com.taskapi.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class CompleteResponseBuilder implements ICompleteResultConsumer {
    private ResponseEntity<Map<String, Object>> response;

    @Override
    public void accept(Task completedTask) {
        TaskResponseWriter writer = new TaskResponseWriter();
        completedTask.accept(writer);
        response = ResponseEntity.ok(writer.build());
    }

    @Override
    public void reject(String reason) {
        response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", reason));
    }

    public ResponseEntity<Map<String, Object>> build() {
        return response;
    }
}
