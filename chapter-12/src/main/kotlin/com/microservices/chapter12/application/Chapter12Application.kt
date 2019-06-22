package com.microservices.chapter12.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Chapter12Application

fun main(args: Array<String>) {
    runApplication<Chapter12Application>(*args)
}
