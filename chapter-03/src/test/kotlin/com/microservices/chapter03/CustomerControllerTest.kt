package com.microservices.chapter03

import com.beust.klaxon.Klaxon
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var customerService: CustomerService

    @Before
    fun setUp() {
        // 원래는 이런 펑션이 없으나 테스트 시 목록을 초기화 하기 위해서 만들었다.
        customerService.reset()
    }

    @Test
    fun getCustomer() {
        val action = mockMvc.perform(get("/customer/3"))
                .andDo(print())
                .andExpect(status().isOk)

        val content = action.andReturn().response.contentAsString
        // https://github.com/cbeust/klaxon
        val json = Klaxon().parse<Customer>(content)
        assertEquals(3, json?.id)
        assertEquals("Microservice", json?.name)
    }

    @Test
    fun getCustomerButNotFound() {
        val action = mockMvc.perform(get("/customer/999")).andDo(print()).andExpect(status().isNotFound)
        val response = action.andReturn().response.contentAsString
        val json = Klaxon().parseJsonObject(response.reader())

        assertEquals("Customer Not Found", json["error"])
        assertEquals("customer '999' not found", json["message"])
    }

    @Test
    fun getCustomers() {
        val customers = requestGetCustomers()
        assertEquals(3, customers?.size)
        assertEquals("Kotlin", customers?.get(0)?.name)
        assertNull(customers?.get(0)?.telephone)
        assertEquals("Microservice", customers?.get(2)?.name)
        assertEquals("+44", customers?.get(2)?.telephone?.countryCode)
    }

    @Test
    fun getCustomersByNameFilter() {
        val action = mockMvc.perform(get("/customers").param("nameFilter", "in"))
                .andDo(print())
                .andExpect(status().isOk)

        val content = action.andReturn().response.contentAsString
        val jsonArray = Klaxon().parseArray<Customer>(content)

        assertEquals(2, jsonArray?.size)
        assertEquals("Kotlin", jsonArray?.get(0)?.name)
        assertEquals("Spring", jsonArray?.get(1)?.name)
    }

    @Test
    fun createCustomer() {
        requestCreateCustomer()
        // get customers
        val customers = requestGetCustomers()
        assertEquals(4, customers?.size)
        assertEquals("Kotlin", customers?.get(0)?.name)
        assertEquals("New Customer", customers?.get(3)?.name)
    }

    @Test
    fun createCustomerButBadRequest1() {
        // 마지막에 "," 있음
        val content = "{ \"id\" : 4, \"customerName\": \"New Customer\",}"
        mockMvc.perform(
                post("/customer").contentType(MediaType.APPLICATION_JSON).content(content)
        ).andDo(print()).andExpect(status().isBadRequest)
    }

    @Test
    fun createCustomerButBadRequest2() {
        // 마지막에 "}" 없음
        val content = "{ \"id\" : 4, \"customerName\": \"New Customer\""
        mockMvc.perform(
                post("/customer").contentType(MediaType.APPLICATION_JSON).content(content)
        ).andDo(print()).andExpect(status().isBadRequest)
    }

    @Test
    fun createCustomerButBadRequest3() {
        // 마지막에 "}" 없음
        val content = "{ \"id\": 4, \"name\": \"New Customer\", \"telephone\" : { \"countryCode\": \"+44\", \"telephoneNumber\": \"7123456789\" }"
        // action
        val action = mockMvc.perform(
                post("/customer").contentType(MediaType.APPLICATION_JSON).content(content)
        ).andDo(print()).andExpect(status().isBadRequest)

        val response = action.andReturn().response.contentAsString
        val json = Klaxon().parseJsonObject(response.reader())

        assertEquals("JSON Error", json["error"])
    }

    @Test
    fun deleteCustomer() {
        // 추가할 Customer 아이디
        val id = 4
        // post
        requestCreateCustomer(id)
        assertEquals(4, requestGetCustomers()?.size)
        // delete
        mockMvc.perform(delete("/customer/$id")).andDo(print()).andExpect(status().isOk)
        // get
        assertEquals(3, requestGetCustomers()?.size)
    }

    @Test
    fun deleteCustomerButNotFound() {
        mockMvc.perform(delete("/customer/9999")).andDo(print()).andExpect(status().isNotFound)
    }

    @Test
    fun updateCustomer() {
        val content = getCustomerJson(Customer(4, "Update Customer", Telephone("+44", "7123456789")))
        mockMvc.perform(put("/customer/2").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print()).andExpect(status().isAccepted)

        val customers = requestGetCustomers()

        assertEquals(3, customers?.size)
        assertEquals("Update Customer", customers?.get(2)?.name)
    }

    @Test
    fun updateCustomerButNotFound() {
        val content = getCustomerJson(Customer(4, "Update Customer", Telephone("+44", "7123456789")))
        mockMvc.perform(put("/customer/999").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print()).andExpect(status().isNotFound)
    }

    private fun requestGetCustomers(nameFilter: String? = null): List<Customer>? {
        val get = get("/customers")
        if (nameFilter != null) {
            get.param("nameFilter", nameFilter)
        }

        val action = mockMvc.perform(get).andDo(print()).andExpect(status().isOk)
        val content = action.andReturn().response.contentAsString
        return Klaxon().parseArray(content)
    }

    private fun requestCreateCustomer(id: Int = 4, name: String = "New Customer") {
        val bodyContent = getCustomerJson(Customer(id, name, Telephone("+44", "7123456789")))
        mockMvc.perform(
                post("/customer").contentType(MediaType.APPLICATION_JSON).content(bodyContent)
        ).andDo(print()).andExpect(status().isCreated)
    }

    private fun getCustomerJson(customer: Customer) = Klaxon().toJsonString(customer)

}