package com.microservices.chapter12

class AccountServiceImpl : AccountService {
    override fun getAccountsByCustomer(customerId: Int): List<Account> =
            listOf(Account(1, 125F), Account(2, 500F))
}