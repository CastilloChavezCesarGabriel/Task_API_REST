package com.taskapi.infrastructure.web.response;

import com.taskapi.application.result.ICreateResultConsumer;
import com.taskapi.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class CreateResponseBuilder implements ICreateResultConsumer {
    private ResponseEntity<Map<String, Object>> response;

    @Override
    public void accept(Task createdTask) {
        TaskResponseWriter writer = new TaskResponseWriter();
        createdTask.accept(writer);
        response = ResponseEntity.status(HttpStatus.CREATED).body(writer.build());
    }

    @Override
    public void reject(String reason) {
        response = ResponseEntity.badRequest().body(Map.of("error", reason));
    }

    public ResponseEntity<Map<String, Object>> build() {
        return response;
    }
}
