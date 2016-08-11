package com.tonitealive.server.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tonitealive.server.web",
        "com.tonitealive.server.data",
        "com.tonitealive.server.domain"})
public class ToniteAliveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToniteAliveApplication.class, args);
    }

}