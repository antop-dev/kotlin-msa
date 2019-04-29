package com.microservices

import org.junit.Test

class Chapter03ApplicationTest {

    @Test
    fun main() {
        // 이미 실행 중인 상태에서 테스트를 수행하면 포트 중복으로 에러남.
        // 그래서 임의로 다른 포트로 띄우도록 함.
        System.setProperty("server.port", "50000")
        main(arrayOf())
    }

}