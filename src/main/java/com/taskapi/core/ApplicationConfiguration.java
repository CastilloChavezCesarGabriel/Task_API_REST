package com.taskapi.core;

import com.taskapi.application.TaskFacade;
import com.taskapi.domain.ITaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public final class ApplicationConfiguration {
    @Bean
    public TaskFacade provide(ITaskRepository repository) {
        return new TaskFacade(repository);
    }
}