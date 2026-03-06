package com.taskapi.infrastructure;

import com.taskapi.application.TaskFacade;
import com.taskapi.domain.visitor.ITaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public TaskFacade provide(ITaskRepository repository) {
        return new TaskFacade(repository);
    }
}