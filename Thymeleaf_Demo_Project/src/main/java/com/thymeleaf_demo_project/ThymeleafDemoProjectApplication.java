package com.thymeleaf_demo_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"Controller", "Services", "Exception"})
@EntityScan(basePackages = "Entity")
@EnableJpaRepositories(basePackages = "Repository")
public class ThymeleafDemoProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafDemoProjectApplication.class, args);
    }

}
