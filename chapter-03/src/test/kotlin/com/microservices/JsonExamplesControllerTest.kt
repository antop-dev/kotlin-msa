package com.microservices

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class JsonExamplesControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun getJson() {
        val json = requestJson("/json")

        assertEquals("hello", json["name"])
        assertNull(json["place"])
    }

    @Test
    fun getJson2() {
        val json = requestJson("/json2")

        assertEquals("hello", json["name"])
        assertEquals("world", json["place"])
    }

    @Test
    fun getJson3() {
        val json = requestJson("/json3")

        assertEquals("hi", json["name"])
        assertEquals("kotlin", json["place"])
    }

    @Test
    fun getJson4() {
        val json = requestJson("/json4")

        assertEquals("more", json.obj("object1")?.get("name"))
        assertEquals("complex", json.obj("object1")?.get("place"))
    }

    private fun requestJson(uri: String): JsonObject {
        val action = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk)
        val content = action.andReturn().response.contentAsString
        return Klaxon().parseJsonObject(content.reader())
    }

}