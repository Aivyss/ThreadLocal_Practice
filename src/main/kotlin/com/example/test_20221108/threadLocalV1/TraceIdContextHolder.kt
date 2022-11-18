package com.example.test_20221108.threadLocalV1

import com.example.test_20221108.trace.TraceId
import com.example.test_20221108.trace.TraceStatus
import org.springframework.stereotype.Component

@Component
class TraceIdContextHolder : TraceIdentification {
    private val context: ThreadLocal<TraceId?> = ThreadLocal()
    private val exception = IllegalStateException("Illegal State: No Trace Id in this thread")

    override fun createNextId(): TraceId {
        val nextTraceId = context.get()?.createNextId() ?: throw exception
        context.set(nextTraceId)

        return nextTraceId
    }

    override fun createPreviousId(): TraceId {
        val previousTraceId = context.get()?.createPreviousId() ?: throw exception
        context.set(previousTraceId)

        return previousTraceId
    }

    override fun isFirstLevel(): Boolean = context.get()?.isFirstLevel() ?: throw exception
    override fun getId(): String = context.get()?.id ?: throw exception
    override fun getLevel(): Int = context.get()?.level ?: throw exception
    override fun begin() {
        val traceId = context.get()

        if (traceId == null) {
            context.set(TraceId())
        } else {
            context.set(
                if (traceId.level == -1) {
                    traceId.createNextId().createNextId()
                } else {
                    traceId.createNextId()
                }
            )
        }
    }

    override fun end(status: TraceStatus) = context.remove()
    override fun getTraceId(): TraceId = context.get() ?: throw exception
}