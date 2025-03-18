package com.swyth.emergencyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmergencyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmergencyServiceApplication.class, args);
	}

}
