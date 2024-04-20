package com.example.MultiThreading.using.CompletableFuture.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*
COnfiguration Indicates that a class declares one or more @Bean methods and may be processed by the 
Spring container to generate bean definitions and service requests for those beans at runtime '
*/
@Configuration
/*
 * @EnableAsync enables Spring's asynchronous method execution capability,
 * similar to
 * functionality found in Spring's
 */
@EnableAsync
public class AsyncConfig {
    /*
     * Bean indicates that a method produces a bean to be managed by the Spring
     * container.
     */
    @Bean(name = "taskExecutor")
    /*
     * Executor is an object that executes submitted Runnable tasks. This interface
     * provides a way of decoupling task submission from the mechanics of how each
     * task will be run, including details of thread use, scheduling, etc. An
     * Executor is normally used instead of explicitly creating threads. For
     * example, rather than invoking new Thread(new RunnableTask()).start() for each
     * of a set of tasks, you might use:
     */
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("userThread-");
        executor.initialize();
        return executor;
    }
}
