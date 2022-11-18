package com.example.test_20221108.threadLocalV1

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceTLV1 @Autowired constructor(
    private val orderRepositoryV1: OrderRepositoryTLV1,
    private val traceRecorderV1: TraceRecorderTLV1,
) {
    fun orderItem(itemId: String) {
        val status = traceRecorderV1.begin("OrderServiceV1.orderItem()")

        try {
            orderRepositoryV1.save(itemId)

            traceRecorderV1.end(status)
        } catch(e: Exception) {
            traceRecorderV1.exception(status, e)

            throw e
        }
    }
}