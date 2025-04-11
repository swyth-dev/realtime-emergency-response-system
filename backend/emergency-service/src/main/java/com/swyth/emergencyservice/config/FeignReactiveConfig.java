package com.swyth.emergencyservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executors;

/**
 * Configuration class for setting up reactive Feign components.
 *
 * This class defines custom configurations required for reactive Feign,
 * including a scheduler for managing blocking operations in a reactive context, that fixed
 * a blocking single thread operation error.
 */
@Configuration
public class FeignReactiveConfig {

    /**
     * Defines a scheduler for managing Feign client operations in a non-blocking
     * reactive context. The scheduler uses a fixed thread pool with a capacity of 10
     * threads to handle blocking operations more efficiently, reducing the risk of
     * thread starvation in reactive pipelines.
     *
     * @return a {@link Scheduler} instance backed by a fixed thread pool for executing
     *         blocking tasks within Feign clients.
     */
    @Bean
    public Scheduler feignScheduler() {
        // Créer un thread pool dédié aux opérations bloquantes
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(10));
    }
}
