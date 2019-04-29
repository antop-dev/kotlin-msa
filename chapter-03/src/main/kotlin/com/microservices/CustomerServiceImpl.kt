package com.microservices

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService {
    companion object {
        val initialCustoms = arrayOf(
                Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "Microservice", Telephone("+44", "7123456789"))
        )
    }

    // 테스트 초기화를 위해여 일단 private로 하지 않는다
    val customers = ConcurrentHashMap<Int, Customer>(initialCustoms.associateBy(Customer::id))

    override fun reset() {
        customers.clear()
        customers[1] = Customer(1, "Kotlin")
        customers[2] = Customer(2, "Spring")
        customers[3] = Customer(3, "Microservice", Telephone("+44", "7123456789"))
    }

    override fun getCustomer(id: Int) = customers[id]

    override fun createCustomer(customer: Customer) {
        customers[customer.id] = customer
    }

    override fun deleteCustomer(id: Int) {
        customers.remove(id)
    }

    override fun updateCustomer(id: Int, customer: Customer) {
        deleteCustomer(id)
        createCustomer(customer)
    }

    override fun searchCustomers(nameFilter: String): List<Customer> =
            customers.filter {
                it.value.name.contains(nameFilter, true)
            }.map(Map.Entry<Int, Customer>::value).toList()
}