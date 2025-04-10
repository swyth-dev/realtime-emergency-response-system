package com.swyth.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * DiscoveryServiceApplication is the entry point for the Discovery Service application.
 * This application acts as a Eureka Server for service discovery, allowing microservices
 * to register themselves and discover other registered services.
 *
 * This class is annotated with:
 * - @SpringBootApplication: Indicates a Spring Boot application.
 * - @EnableEurekaServer: Activates the Netflix Eureka Server for service discovery capabilities.
 *
 * The main method initializes and runs the Spring Boot application.
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }

}
