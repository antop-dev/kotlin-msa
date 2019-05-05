package com.microservices.chapter03

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JsonExamplesController {

    @GetMapping("/json")
    fun getJson() = SimpleObject()

    @GetMapping("/json2")
    fun getJson2() = SimpleObject2()

    @GetMapping("/json3")
    fun getJson3() = SimpleObject3("hi", "kotlin")

    @GetMapping("/json4")
    fun getJson4() = ComplexObject(object1 = SimpleObject3("more", "complex"))

}