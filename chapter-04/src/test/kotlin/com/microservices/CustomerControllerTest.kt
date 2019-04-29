package com.microservices

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun getCustomer() {
        webClient.get().uri("/customer/2").exchange().expectStatus().isOk
                .expectBody<Customer>().isEqualTo(Customer(2, "Spring"))
    }

    @Test
    fun getCustomers() {
        val result = webClient.get().uri("/customers").exchange().expectStatus().isOk
                .expectBody<List<Customer>>().returnResult()
    }

    @Test
    fun createCustomer() {
        val customer = Customer(4, "New Customer", Telephone("+41", "1234567890"))
        webClient.post()
                .uri("/customer")
                .body(Mono.just(customer), Customer::class.java)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange().expectStatus().isCreated
    }
}