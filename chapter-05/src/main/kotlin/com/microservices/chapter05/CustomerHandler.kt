package com.microservices.chapter05

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI

@Component
class CustomerHandler(val customerService: CustomerService) {

    fun get(serverRequest: ServerRequest): Mono<ServerResponse> =
            customerService.getCustomer(serverRequest.pathVariable("id").toInt())
                    .flatMap { ServerResponse.ok().body(BodyInserters.fromObject(it)) }
                    .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build())

    fun create(serverRequest: ServerRequest): Mono<ServerResponse> =
            customerService.createCustomer(serverRequest.bodyToMono(Customer::class.java)).flatMap {
                ServerResponse.created(URI.create("/customer/${it.id}")).build()
            }

    fun delete(serverRequest: ServerRequest): Mono<ServerResponse> =
            customerService.deleteCustomer(serverRequest.pathVariable("id").toInt())
                    .flatMap {
                        if (it) ServerResponse.ok().build()
                        else ServerResponse.status(HttpStatus.NOT_FOUND).build()
                    }

    fun search(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(
                    customerService.searchCustomers(serverRequest.queryParam("nameFilter").orElse("")),
                    Customer::class.java
            )

}