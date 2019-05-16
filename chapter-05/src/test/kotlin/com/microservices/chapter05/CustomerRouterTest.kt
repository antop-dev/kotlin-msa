package com.microservices.chapter05

import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class CustomerRouterTest {
    @Autowired
    private lateinit var webClient: WebTestClient
    @Autowired
    private lateinit var repository: CustomerRepository

    @After
    fun tearDown() {
        repository.initializeRepository()
    }

    @Test
    fun get() {
        webClient.get().uri("/customer/1").exchange().expectStatus().isOk
                .expectBody<Customer>().isEqualTo(Customer(1, "Kotlin"))
    }

    @Test
    fun create() {
        val customer = Customer(4, "New Customer", Telephone("+41", "1234567890"))
        webClient.post()
                .uri("/customer")
                .body(Mono.just(customer), Customer::class.java)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange()
                .expectStatus().isCreated
                .expectHeader().value("Location", Matchers.equalTo("/customer/${customer.id}"))

        val name = webClient.get().uri("/customer/${customer.id}").exchange().expectStatus().isOk
                .expectBody<Customer>().returnResult().responseBody?.name

        Assert.assertEquals(customer.name, name)
    }

    @Test
    fun delete() {
        val id = 2
        webClient.delete().uri("/customer/$id").exchange().expectStatus().isOk
        webClient.get().uri("/customer/$id").exchange().expectStatus().isNotFound
    }

    @Test
    fun customers() {
        val list = webClient.get().uri("/customers").exchange().expectStatus().isOk
                .expectBodyList(Customer::class.java).returnResult().responseBody
        Assert.assertEquals("Kotlin", list[0].name)
        Assert.assertEquals("Spring", list[1].name)

    }
}