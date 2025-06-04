package com.ajoufinder.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AjouFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AjouFinderApplication.class, args);
    }

}
