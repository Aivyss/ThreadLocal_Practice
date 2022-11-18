package com.example.test_20221108.threadLocalV1

import com.example.test_20221108.trace.TraceId
import com.example.test_20221108.trace.TraceStatus
import org.springframework.stereotype.Component

@Component
class TraceIdContextHolderImpl : TraceIdContextHolder {
    private val context: ThreadLocal<TraceId?> = ThreadLocal()
    private val exception = IllegalStateException("Illegal State: No Trace Id in this thread")


    override fun createPreviousId(): TraceId {
        val previousTraceId = context.get()?.createPreviousId() ?: throw exception
        context.set(previousTraceId)

        return previousTraceId
    }

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
    override fun getTraceId(): TraceId = context.get() ?: throw exception
    override fun remove() = context.remove()
}