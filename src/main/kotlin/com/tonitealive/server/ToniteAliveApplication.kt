package com.tonitealive.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class ToniteAliveApplication {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(ToniteAliveApplication::class.java, *args)
        }
    }

}