package com.example.test_20221108.trace

class TraceStatus(
    val traceId: TraceId,
    val startTimeMs: Long, // start log time
    val message: String, // log message
)