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

    @Bean
    public Scheduler feignScheduler() {
        // Créer un thread pool dédié aux opérations bloquantes
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(10));
    }
}
