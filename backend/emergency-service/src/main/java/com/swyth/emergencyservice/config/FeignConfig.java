package com.swyth.emergencyservice.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignConfig class provides the configuration for Feign clients in the application.
 *
 * This class customizes the Feign client by registering beans for request and response
 * encoding/decoding and error handling. The configurations ensure that Feign clients
 * can seamlessly integrate with the application's data format and handle errors
 * appropriately.
 */
@Configuration
public class FeignConfig {

    /**
     * Configures and provides a custom Feign decoder bean.
     *
     * The decoder leverages the Spring framework's message converters
     * to handle the deserialization of HTTP response bodies into Java
     * objects. This allows Feign clients to seamlessly work with various
     * data formats (e.g., JSON, XML) used in HTTP responses.
     *
     * @return a {@link Decoder} implementation that uses Spring's {@link HttpMessageConverters}
     *         for converting response data.
     */
    @Bean
    public Decoder feignDecoder() {
        return new SpringDecoder(HttpMessageConverters::new);
    }

    /**
     * Configures and provides a custom Feign encoder bean.
     *
     * This encoder leverages the Spring framework's message converters
     * to handle the serialization of Java objects into HTTP request body
     * representations. This allows Feign clients to seamlessly send data
     * in various formats (e.g., JSON, XML) required by the endpoint.
     *
     * @return an {@link Encoder} implementation that uses Spring's {@link HttpMessageConverters}
     *         for converting request data.
     */
    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(HttpMessageConverters::new);
    }

    /**
     * Registers a custom Feign {@link ErrorDecoder} bean to handle error responses.
     *
     * This method provides a custom error decoder implementation that parses
     * HTTP error responses and maps specific status codes to appropriate exceptions.
     * It enhances error handling for Feign clients by enabling the application to
     * interpret and respond to errors in a controlled manner.
     *
     * @return an instance of {@link CustomErrorDecoder} for decoding Feign client errors
     */
    @Bean
    public ErrorDecoder customErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
