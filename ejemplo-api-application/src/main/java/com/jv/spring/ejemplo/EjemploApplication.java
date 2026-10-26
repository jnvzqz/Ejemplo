package com.jv.spring.ejemplo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.jv.spring.ejemplo")
@EnableJpaRepositories("com.jv.spring.ejemplo")
public class EjemploApplication {

    public static void main(String[] args) {
        SpringApplication.run(EjemploApplication.class, args);
    }
}
