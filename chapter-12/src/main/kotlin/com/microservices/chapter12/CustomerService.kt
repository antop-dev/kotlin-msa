package com.microservices.chapter12

interface CustomerService {
    fun getCustomer(id: Int): Customer
}