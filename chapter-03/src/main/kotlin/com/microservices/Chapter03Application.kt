package com.microservices

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Chapter03Application

fun main(args: Array<String>) {
    runApplication<Chapter03Application>(*args)
}