package com.microservices.chapter10

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.stereotype.Component

@Component
class GreetingsServiceHealthIndicator : AbstractHealthIndicator() {
    @Autowired
    lateinit var greetingsService: GreetingsService

    override fun doHealthCheck(builder: Health.Builder) {
        val message = greetingsService.getGreeting()
        builder.up()
        builder.withDetail("message", message)
    }
}