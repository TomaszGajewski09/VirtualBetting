package com.projekt.VirtualBetting;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition // http://localhost:8080/swagger-ui/index.html
@SpringBootApplication
@EnableScheduling
public class VirtualBettingApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualBettingApplication.class, args);
	}

}
