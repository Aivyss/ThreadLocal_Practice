package com.example.test_20221108.vv1

import com.example.test_20221108.trace.TraceIdContext
import com.example.test_20221108.trace.TraceStatus
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class TraceRecorderVV1(private val traceIdContext: TraceIdContext) {
    private val log = LoggerFactory.getLogger(TraceRecorderVV1::class.java)
    private val COMPLETE_PREFIX = "<--"
    private val START_PREFIX = "-->"
    private val EX_PREFIX = "<x-"

    fun begin(message: String): TraceStatus {
        val traceContext = traceIdContext
        val startTimeMs = System.currentTimeMillis()
        traceContext.createNextId()

        log.info("[{}] {}{}", traceContext.getId(), addSpace(START_PREFIX, traceContext.getLevel()), message)

        return TraceStatus(traceContext.getTraceId(), startTimeMs, message)
    }

    fun end(status: TraceStatus) {
        complete(status, null)
        traceIdContext.createPreviousId()
    }

    fun exception(status: TraceStatus, e: Exception) {
        complete(status, e)
        traceIdContext.createPreviousId()
    }

    private fun complete(status: TraceStatus, e: Exception?) {
        val stopTime = System.currentTimeMillis()
        val resultTime = stopTime - status.startTimeMs
        val traceId = status.traceId

        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.id, addSpace(COMPLETE_PREFIX, traceId.level), status.message, resultTime)
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.id, addSpace(EX_PREFIX, traceId.level), status.message, resultTime, e.toString())
        }
    }

    private fun addSpace(prefix: String, level: Int): String {
        val sb = StringBuilder()
//        val levelTemp = if (level == -1) 0 else level
        for (i in 0 until level) {
            sb.append(if (i == level - 1) "|${prefix}" else "|   ")
        }

        return sb.toString()
    }
}