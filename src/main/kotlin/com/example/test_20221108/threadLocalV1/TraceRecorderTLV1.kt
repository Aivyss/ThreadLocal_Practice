package com.example.test_20221108.threadLocalV1

import com.example.test_20221108.trace.TraceStatus
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class TraceRecorderTLV1(private val contextHolder: TraceIdContextHolder) {
    private val log = LoggerFactory.getLogger(TraceRecorderTLV1::class.java)
    private val completePrefix = "<--"
    private val startPrefix = "-->"
    private val exPrefix = "<x-"

    fun begin(message: String): TraceStatus {
        contextHolder.begin()
        val startTimeMs = System.currentTimeMillis()

        log.info("[{}] {}{}", contextHolder.getId(), addSpace(startPrefix, contextHolder.getLevel()), message)

        return TraceStatus(contextHolder.getTraceId(), startTimeMs, message)
    }

    fun end(status: TraceStatus) {
        complete(status, null)
        contextHolder.createPreviousId()
    }

    fun exception(status: TraceStatus, e: Exception) {
        complete(status, e)
        contextHolder.createPreviousId()
    }

    private fun complete(status: TraceStatus, e: Exception?) {
        val stopTime = System.currentTimeMillis()
        val resultTime = stopTime - status.startTimeMs
        val traceId = status.traceId

        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.id, addSpace(completePrefix, traceId.level), status.message, resultTime)
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.id, addSpace(exPrefix, traceId.level), status.message, resultTime, e.toString())
        }
    }

    private fun addSpace(prefix: String, level: Int): String {
        val sb = StringBuilder()

        for (i in 0 until level) {
            sb.append(if (i == level - 1) "|${prefix}" else "|   ")

            if (level == 0 && (prefix == completePrefix || prefix == exPrefix)) {
                contextHolder.remove()
            }
        }

        return sb.toString()
    }
}