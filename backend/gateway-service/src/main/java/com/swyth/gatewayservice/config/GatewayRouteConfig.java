package com.swyth.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining custom Gateway routes.
 * This class utilizes Spring Cloud Gateway's RouteLocator to define
 * and register routes for different services.
 *
 * The configuration defines routes for the following services:
 * 1. Hospital Service
 *    - Routes requests with paths matching "/v1/hospitals/**" and "/v1/bed-availabilities/**"
 *      to the "hospital-service" load balancer.
 *    - Routes requests with paths matching "/v1/specializations/**" to the "hospital-service" load balancer.
 *
 * 2. Emergency Service
 *    - Routes requests with paths matching "/v1/bed-reservations/**" to the "emergency-service" load balancer.
 *
 * Additional routes can be added as needed by registering them in this configuration.
 */
@Configuration
public class GatewayRouteConfig {

    /**
     * Configures custom routes for services using Spring Cloud Gateway's RouteLocator.
     *
     * This method defines routes that map specific paths to their corresponding services
     * through load balancers, enabling communication between the API Gateway and backend services.
     *
     * @param builder the RouteLocatorBuilder used to build and configure the routes
     * @return a RouteLocator containing the defined service routes
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Routes pour hospital-service
                .route("hospital-route", r -> r
                        .path("/v1/hospitals/**", "/v1/bed-availabilities/**")
                        .uri("lb://hospital-service"))
                .route("specialization-route", r -> r
                        .path("/v1/specializations/**")
                        .uri("lb://hospital-service"))

                // Routes pour emergency-service
                .route("emergency-route", r -> r
                        .path("/v1/bed-reservations/**")
                        .uri("lb://emergency-service"))

                // Vous pouvez ajouter d'autres routes selon vos besoins
                .build();
    }
}
