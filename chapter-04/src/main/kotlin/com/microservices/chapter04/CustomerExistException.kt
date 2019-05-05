package com.microservices.chapter04

class CustomerExistException(override val message: String) : Exception(message)