package com.microservices.chapter10

import org.springframework.stereotype.Service
import java.util.*

@Service
class GreetingsServiceImpl : GreetingsService {
    companion object {
        private val greetingsMessages = arrayOf("Hello", "Olá", "Namaste", "Hola")
    }

    private fun random(max: Int): Int = Random().nextInt(max) + 1

    override fun getGreeting()= greetingsMessages[random(4)]
}