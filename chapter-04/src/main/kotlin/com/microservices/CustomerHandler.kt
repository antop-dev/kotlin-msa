package com.microservices

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.publisher.onErrorResume
import java.net.URI

@Component
class CustomerHandler(private val customerService: CustomerService) {
    fun get(serverRequest: ServerRequest): Mono<ServerResponse> =
            customerService.getCustomer(serverRequest.pathVariable("id").toInt())
                    .flatMap { ok().body(fromObject(it)) }
                    .switchIfEmpty(ServerResponse.notFound().build())

    fun search(serverRequest: ServerRequest): Mono<ServerResponse> =
            ok().body(
                    customerService.searchCustomers(
                            serverRequest.queryParam("nameFilter").orElse("")
                    ),
                    Customer::class.java
            )

    fun create(serverRequest: ServerRequest): Mono<ServerResponse> =
            customerService.createCustomer(serverRequest.bodyToMono()).flatMap {
                ServerResponse.created(URI.create("/functional/customer/${(it as Customer).id}")).build()
            }.onErrorResume(Exception::class) {
                badRequest().body(
                        fromObject(
                                ErrorResponse("error creating customer", it.message ?: "error")
                        )
                )
            }

}