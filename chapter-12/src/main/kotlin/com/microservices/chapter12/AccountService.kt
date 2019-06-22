package com.microservices.chapter12

interface AccountService {
    fun getAccountsByCustomer(customerId: Int): List<Account>
}