package com.example.test_20221108.trace

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TraceRecorderV2 {
    private val log = LoggerFactory.getLogger(TraceRecorderV2::class.java)
    private val COMPLETE_PREFIX = "<--"
    private val START_PREFIX = "-->"
    private val EX_PREFIX = "<x-"

    fun begin(message: String): TraceStatus {
        val traceId = TraceId().createNextId()
        val startTimeMs = System.currentTimeMillis()

        log.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.level), message)

        return TraceStatus(traceId, startTimeMs, message)
    }

    fun beginSync(beforeTraceId: TraceId, message: String): TraceStatus {
        val nextTraceId = beforeTraceId.createNextId()
        val startTimeMs = System.currentTimeMillis()

        log.info("[{}] {}{}", nextTraceId.id, addSpace(START_PREFIX, nextTraceId.level), message)

        return TraceStatus(nextTraceId, startTimeMs, message)
    }

    fun end(status: TraceStatus) {
        complete(status, null)
    }

    fun exception(status: TraceStatus, e: Exception) {
        complete(status, e)
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

        for (i in 0 until level) {
            sb.append(if (i == level - 1) "|${prefix}" else "|   ")
        }

        return sb.toString()
    }
}