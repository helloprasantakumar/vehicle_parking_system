package com.prasanta.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.prasanta.parking.repository")
@EntityScan(basePackages = "com.prasanta.parking.model")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.prasanta.parking.WebApplication.class, args);
    }

}
