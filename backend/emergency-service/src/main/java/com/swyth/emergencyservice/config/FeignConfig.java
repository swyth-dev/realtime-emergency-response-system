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

    @Bean
    public Decoder feignDecoder() {
        return new SpringDecoder(HttpMessageConverters::new);
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(HttpMessageConverters::new);
    }

    @Bean
    public ErrorDecoder customErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
