package com.taskapi.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.taskapi")
public class TaskApiApplication {
    public static void main(String[] arguments) {
        SpringApplication.run(TaskApiApplication.class, arguments);
    }
}