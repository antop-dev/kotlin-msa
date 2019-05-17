package com.microservices.chapter05

import com.mongodb.client.result.DeleteResult
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import javax.annotation.PostConstruct

@Repository
class CustomerRepository(private val template: ReactiveMongoTemplate) {
    companion object {
        val initialCustomers = listOf(
                Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "Microservice", Telephone("+44", "7123456789"))
        )
    }

    @PostConstruct
    fun initializeRepository() {
        template.remove(Query(Criteria.where("_id").lte(4)), Customer::class.java).subscribe()
        initialCustomers.map(Customer::toMono).map(this::create).map(Mono<Customer>::subscribe)
    }

    fun create(customer: Mono<Customer>): Mono<Customer> = template.save(customer)

    fun findById(id: Int): Mono<Customer> = template.findById(id, Customer::class.java)

    fun deleteById(id: Int): Mono<DeleteResult> = template.remove(Query(Criteria.where("_id").isEqualTo(id)), Customer::class.java)

    fun findCustomer(nameFilter: String): Flux<Customer> =
            template.find(
                    Query(Criteria.where("name").regex(".*$nameFilter.*", "i")).with(Sort.by(Sort.Order.asc("id"))),
                    Customer::class.java
            )
}

