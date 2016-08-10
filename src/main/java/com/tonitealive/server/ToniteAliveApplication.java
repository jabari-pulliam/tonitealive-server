package com.tonitealive.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ToniteAliveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToniteAliveApplication.class, args);
    }

}