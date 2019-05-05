package com.microservices.chapter02

import com.github.javafaker.Faker
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest
@ActiveProfiles("production")
class FirstControllerProdTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    // val = final
    private val faker: Faker = Faker()

    @Test
    fun hello() {
        val name = faker.name().firstName()
        // action
        val action = mockMvc.perform(get("/user/{name}", name))
                .andDo(print())
        // verify
        action.andExpect(status().isOk)

        val content = action.andReturn().response.contentAsString
        assertEquals("welcome $name (2)", content)
    }
}