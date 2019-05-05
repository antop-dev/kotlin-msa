package com.microservices.chapter02

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AdvanceService::class])
class AdvanceServiceTest {
    @Autowired
    private lateinit var service: ServiceInterface

    @Test
    fun getHello() {
        var ret = service.getHello("antop")
        assertEquals("hi antop (2)", ret)

        ret = service.getHello("antop")
        assertEquals("hi antop (3)", ret)

        ret = service.getHello("antop")
        assertEquals("hi antop (4)", ret)
    }

}