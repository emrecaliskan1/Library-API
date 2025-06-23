package com.emre.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = { "com.emre" })
@EntityScan(basePackages = { "com.emre" })
@EnableJpaRepositories(basePackages = { "com.emre" })
@SpringBootApplication
@EnableScheduling
public class LibraryApiApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiApplicationStarter.class, args);
    }

}
