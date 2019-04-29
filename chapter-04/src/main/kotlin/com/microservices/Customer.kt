package com.microservices

data class Customer(var id: Int = 0, val name: String = "", val telephone: Telephone? = null)

data class Telephone(var countryCode: String = "", val telephoneNumber: String = "")