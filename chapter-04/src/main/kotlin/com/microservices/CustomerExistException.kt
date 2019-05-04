package com.microservices

class CustomerExistException(override val message: String) : Exception(message)