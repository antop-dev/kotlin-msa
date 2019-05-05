package com.microservices.chapter04

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService {
    companion object {
        val initialCustomers = arrayOf(
                Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "Microservice", Telephone("+44", "7123456789"))
        )
    }

    private var customers = ConcurrentHashMap<Int, Customer>(initialCustomers.associateBy(Customer::id))

    override fun reset() {
        customers = ConcurrentHashMap(initialCustomers.associateBy(Customer::id))
    }

    override fun getCustomer(id: Int) = customers[id]?.toMono() ?: Mono.empty()

    override fun searchCustomers(nameFilter: String): Flux<Customer> =
            customers.filter {
                it.value.name.contains(nameFilter, true)
            }.map(Map.Entry<Int, Customer>::value).toFlux()

    override fun createCustomer(customerMono: Mono<Customer>): Mono<*> =
            customerMono.flatMap {
                if (customers[it.id] == null) {
                    customers[it.id] = it
                    it.toMono()
                } else {
                    Mono.error(CustomerExistException("Customer ${it.id} already exist"))
                }
            }

}
