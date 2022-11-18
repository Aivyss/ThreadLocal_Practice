package com.example.test_20221108.threadLocalV1

import com.example.test_20221108.trace.TraceId
import com.example.test_20221108.trace.TraceStatus

interface TraceIdContextHolder {
    fun createPreviousId(): TraceId
    fun getId(): String
    fun getLevel(): Int
    fun begin()
    fun getTraceId(): TraceId
    fun remove()
}