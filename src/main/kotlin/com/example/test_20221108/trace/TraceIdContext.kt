package com.example.test_20221108.trace

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component

@Component
@Scope("request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class TraceIdContext {
    private var traceId: TraceId = TraceId()

    fun createNextId(): TraceId {
        traceId = traceId.createNextId()

        return traceId
    }

    fun createPreviousId(): TraceId {
        traceId = traceId.createPreviousId()

        return traceId
    }

    fun isFirstLevel(): Boolean = traceId.isFirstLevel()
    fun getId(): String = traceId.id
    fun getLevel(): Int = traceId.level
    fun getTraceId(): TraceId = traceId
}