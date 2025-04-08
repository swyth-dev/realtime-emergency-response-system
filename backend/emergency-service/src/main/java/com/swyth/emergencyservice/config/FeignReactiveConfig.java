package com.swyth.emergencyservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executors;

@Configuration
public class FeignReactiveConfig {

    @Bean
    public Scheduler feignScheduler() {
        // Créer un thread pool dédié aux opérations bloquantes
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(10));
    }
}
