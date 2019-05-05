package com.microservices.chapter04

import org.hamcrest.Matchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Mono


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerRouterTest {
    val logger: Logger = LoggerFactory.getLogger(CustomerRouterTest::class.java)

    @Rule
    @JvmField
    val watchman: TestWatcher = object : TestWatcher() {
        override fun starting(description: Description?) {
            logger.info("Run Test {}...", description)
        }
        override fun succeeded(description: Description?) {
            logger.info("Test {} succeeded.", description)
        }
        override fun failed(e: Throwable?, description: Description?) {
            logger.error("Test {} failed with {}.", description, e)
        }
    }

    @Autowired
    private lateinit var webClient: WebTestClient
    @Autowired
    private lateinit var customerService: CustomerService

    @Before
    fun setUp() {
        // CustomerServiceImpl 클래스가 상태를 가지고 있다.
        // 원래는 이런 펑션이 없으나 테스트 시 목록을 초기화 하기 위해서 만들었다.
        customerService.reset()
    }

    @Test
    fun get() {
        webClient.get().uri("/functional/customer/2").exchange().expectStatus().isOk
                .expectBody<Customer>().isEqualTo(Customer(2, "Spring"))
    }

    @Test
    fun search() {
        val list = webClient.get().uri("/functional/customers").exchange().expectStatus().isOk
                .expectBodyList<Customer>().returnResult().responseBody
        assertEquals("Spring", list[1].name)
    }

    @Test
    fun create() {
        val customer = Customer(4, "New Customer", Telephone("+41", "1234567890"))
        webClient.post()
                .uri("/functional/customer")
                .body(Mono.just(customer), Customer::class.java)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange()
                .expectStatus().isCreated
                .expectHeader().value("Location", Matchers.equalTo("/functional/customer/${customer.id}"))

    }

    @Test
    fun createExist() {
        val customer = Customer(1, "New Customer", Telephone("+41", "1234567890"))
        webClient.post()
                .uri("/functional/customer")
                .body(Mono.just(customer), Customer::class.java)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange()
                .expectStatus().isBadRequest
                .expectBody<ErrorResponse>()
                .isEqualTo(
                        ErrorResponse("error creating customer", "Customer ${customer.id} already exist")
                )

    }

}