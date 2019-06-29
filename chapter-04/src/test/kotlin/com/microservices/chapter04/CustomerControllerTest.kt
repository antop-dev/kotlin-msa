package com.microservices.chapter04

import org.junit.Before
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
    @Autowired
    private lateinit var customerService: CustomerService

    @Before
    fun setUp() {
        // 원래는 이런 펑션이 없으나 테스트 시 목록을 초기화 하기 위해서 만들었다.
        customerService.reset()
    }

    @Test
    fun getCustomer() {
        webClient.get().uri("/customer/2").exchange().expectStatus().isOk
                .expectBody<Customer>().isEqualTo(Customer(2, "Spring"))
    }

    @Test
    fun getCustomers() {
        val list = webClient.get().uri("/customers").exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("\$[0].name", "Kotlin").hasJsonPath()
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