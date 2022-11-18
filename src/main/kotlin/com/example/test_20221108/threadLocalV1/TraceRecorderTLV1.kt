package com.example.test_20221108.threadLocalV1

import com.example.test_20221108.trace.TraceStatus
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class TraceRecorderTLV1(private val contextHolder: TraceIdentification) {
    private val log = LoggerFactory.getLogger(TraceRecorderTLV1::class.java)
    private val COMPLETE_PREFIX = "<--"
    private val START_PREFIX = "-->"
    private val EX_PREFIX = "<x-"

    fun begin(message: String): TraceStatus {
        contextHolder.begin()
        val startTimeMs = System.currentTimeMillis()

        log.info("[{}] {}{}", contextHolder.getId(), addSpace(START_PREFIX, contextHolder.getLevel()), message)

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