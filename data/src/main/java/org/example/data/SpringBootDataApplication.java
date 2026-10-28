package org.example.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.example.data"})
@EntityScan(basePackages = {"org.example.data.entities"})
@EnableJpaRepositories(basePackages = {"org.example.data.repository"})
public class SpringBootDataApplication {

    static void main(String[] args) {
        SpringApplication.run(SpringBootDataApplication.class, args);
    }
}