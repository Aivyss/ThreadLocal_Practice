package com.example.test_20221108.v2

import com.example.test_20221108.trace.TraceId
import com.example.test_20221108.trace.TraceRecorderV2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryV2 @Autowired constructor(
    private val traceRecorderV2: TraceRecorderV2,
) {

    fun save(itemId: String, traceId: TraceId) {
        val status = traceRecorderV2.beginSync(traceId, "OrderRepositoryV1.save()")

        try {
            if ("ex" == itemId) throw IllegalStateException("exception!!")
            sleep(1000L)

            traceRecorderV2.end(status)
        } catch (e: Exception) {
            traceRecorderV2.exception(status, e)

            throw e
        }

    }

    private fun sleep(millis: Long) {
        Thread.sleep(millis)
    }
}

