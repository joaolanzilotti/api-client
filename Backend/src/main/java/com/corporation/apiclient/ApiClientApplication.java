package com.corporation.apiclient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiClientApplication.class, args);
    }

}
