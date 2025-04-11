package com.swyth.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	/**
	 * Creates a {@link DiscoveryClientRouteDefinitionLocator} bean to dynamically define routes
	 * based on services registered with a discovery client.
	 *
	 * @param rdc the reactive discovery client used to interact with the service registry
	 * @param dlp the properties to customize the discovery locator's behavior
	 * @return a {@link DiscoveryClientRouteDefinitionLocator} instance that defines routes based on discovered services
	 */
	@Bean
	DiscoveryClientRouteDefinitionLocator locator(
			ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
	}
}
