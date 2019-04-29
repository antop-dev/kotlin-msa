package com.microservices.chapter02

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@SpringBootApplication
open class Chapter02Application {
    @Bean
    @Profile("development")
//    @ConditionalOnExpression("#{'\${service.message.type}'=='simple'}")
    open fun exampleService(): ServiceInterface = ExampleService()

    @Bean
    @Profile("production")
//    @ConditionalOnExpression("#{'\${service.message.type}'=='advance'}")
    open fun advanceService(): ServiceInterface = AdvanceService()
}

@Controller
class FirstController {
    @Autowired
    lateinit var service: ServiceInterface

    @GetMapping(value = ["user/{name}"])
    @ResponseBody
    fun hello(@PathVariable name: String) = service.getHello(name)
}

fun main(args: Array<String>) {
    runApplication<Chapter02Application>(*args)
}
