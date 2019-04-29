package com.microservices.chapter02.chapter02

import com.microservices.chapter02.ExampleService
import com.microservices.chapter02.ServiceInterface
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ExampleService::class])
class ExampleServiceTest {
    @Autowired
    private lateinit var service: ServiceInterface

    @Test
    fun getHello() {
        val ret = service.getHello("antop")
        assertEquals("hi antop", ret)
    }
}