package com.swyth.emergencyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The EmergencyServiceApplication class serves as the entry point for the Emergency Service
 * application. It configures the Spring Boot application as a discovery client
 * and enables Feign clients for declarative REST client functionality.
 *
 * Annotations:
 * - @SpringBootApplication: Marks this as a Spring Boot application.
 * - @EnableFeignClients: Enables the detection of Feign clients in the application.
 * - @EnableDiscoveryClient: Configures the application to register itself as a discovery client
 *   in a service registry, allowing it to interact with other services.
 *
 * The main method initializes and runs the Spring Boot application.
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class EmergencyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmergencyServiceApplication.class, args);
	}

}
