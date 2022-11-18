package com.example.test_20221108.v2

import com.example.test_20221108.trace.TraceId
import com.example.test_20221108.trace.TraceRecorderV2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceV2 @Autowired constructor(
    private val orderRepositoryV2: OrderRepositoryV2,
    private val traceRecorderV2: TraceRecorderV2,
) {
    fun orderItem(itemId: String, traceId: TraceId) {
        val status = traceRecorderV2.beginSync(traceId, "OrderServiceV1.orderItem()")

        try {
            orderRepositoryV2.save(itemId, status.traceId)

            traceRecorderV2.end(status)
        } catch(e: Exception) {
            traceRecorderV2.exception(status, e)

            throw e
        }
    }
}