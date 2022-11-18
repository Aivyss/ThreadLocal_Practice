package com.example.test_20221108.threadLocalV1

import com.example.test_20221108.trace.TraceId
import com.example.test_20221108.trace.TraceStatus

interface TraceIdentification {
    fun createNextId(): TraceId
    fun createPreviousId(): TraceId
    fun isFirstLevel(): Boolean
    fun getId(): String
    fun getLevel(): Int
    fun begin()
    fun end(status: TraceStatus)
    fun getTraceId(): TraceId
}