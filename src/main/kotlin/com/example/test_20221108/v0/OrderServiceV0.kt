package com.example.test_20221108.v0

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceV0 @Autowired constructor(
    private val orderRepositoryV0: OrderRepositoryV0
) {
    fun orderItem(itemId: String) {
        orderRepositoryV0.save(itemId)
    }
}