package com.swyth.emergencyservice.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
