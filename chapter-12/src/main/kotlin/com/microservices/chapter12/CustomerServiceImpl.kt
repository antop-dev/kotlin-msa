package com.microservices.chapter12

class CustomerServiceImpl(val accountService: AccountService) : CustomerService {
    override fun getCustomer(id: Int): Customer {
        val accounts = accountService.getAccountsByCustomer(id)
        return Customer(id, "customer-$id", accounts)
    }
}