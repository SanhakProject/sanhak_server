package com.github.sanhak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SanhakApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanhakApplication.class, args);
    }

}
