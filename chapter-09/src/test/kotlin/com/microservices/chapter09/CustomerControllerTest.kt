package com.microservices.chapter09

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @MockBean
    lateinit var customerService: CustomerService

    @Test
    fun `mock mvc should be configured`() {

    }

    @Test
    fun `we should GET a customer by id`() {
        given(customerService.getCustomer(1))
                .willReturn(Customer(1, "mock customer"))

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.id").value(1))
                .andExpect(jsonPath("\$.name").value("mock customer"))
                .andDo(print())

        then(customerService).should(times(1)).getCustomer(1)
        then(customerService).shouldHaveNoMoreInteractions()

        reset(customerService)
    }

    @Test
    fun `we should GET a list of customers`() {
        BDDMockito.given(customerService.getAllCustomers())
                .willReturn(listOf(
                        Customer(1, "test"),
                        Customer(2, "mocks")
                ))

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$[0].id").value(1))
                .andExpect(jsonPath("\$[0].name").value("test"))
                .andExpect(jsonPath("\$[1].id").value(2))
                .andExpect(jsonPath("\$[1].name").value("mocks"))
                .andDo(print())

        // customerService.getAllCustomers() 를 한번 호출 했다.
        then(customerService).should(times(1)).getAllCustomers()
        // 더 이상 아무 액션도 없었다.
        then(customerService).shouldHaveNoMoreInteractions()

        reset(customerService)
    }
}


