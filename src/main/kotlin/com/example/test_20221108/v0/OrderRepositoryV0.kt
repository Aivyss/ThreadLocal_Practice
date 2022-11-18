package com.example.test_20221108.v0

import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryV0 {

    fun save(itemId: String) {
        if ("ex" == itemId) throw IllegalStateException("exception!!")

        sleep(1000L)
    }

    private fun sleep(millis: Long) {
        Thread.sleep(millis)
    }
}

