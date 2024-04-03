package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerApplication {
    protected ServerApplication() {
        // SpringBoot's default constructor
    }
    /**
     * Main method.
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
