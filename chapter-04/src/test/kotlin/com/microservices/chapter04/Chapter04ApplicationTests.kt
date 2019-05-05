package com.microservices.chapter04

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureWebTestClient
class Chapter04ApplicationTests {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun contextLoads() {
    }

    @Test
    fun index() {
        val result = webClient.get().uri("/index.html").exchange().expectStatus().isOk
                .expectBody(String::class.java).returnResult()
        val document: Document = Jsoup.parse(result.responseBody)

        assertEquals("Hello World", document.head().select("title").text())
        assertEquals("Reacitve Static Content", document.body().text())
    }
}
