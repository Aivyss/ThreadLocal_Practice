package com.example.test_20221108.v1

import com.example.test_20221108.trace.TraceRecorderV1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryV1 @Autowired constructor(
    private val traceRecorderV1: TraceRecorderV1
){

    fun save(itemId: String) {
        val status = traceRecorderV1.begin("OrderRepositoryV1.save()")

        try {
            if ("ex" == itemId) throw IllegalStateException("exception!!")
            sleep(1000L)

            traceRecorderV1.end(status)
        } catch (e: Exception) {
            traceRecorderV1.exception(status, e)

            throw e
        }

    }

    private fun sleep(millis: Long) {
        Thread.sleep(millis)
    }
}

