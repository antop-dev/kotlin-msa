package com.microservices.chapter03

class SimpleObject2 {
    val name = "hello"
    private val place = "world"
    // public getXxxx()를 직렬화다
    fun getPlace() = place
}
