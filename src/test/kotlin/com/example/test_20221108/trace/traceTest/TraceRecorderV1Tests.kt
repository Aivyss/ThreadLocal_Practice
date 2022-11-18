package com.example.test_20221108.trace.traceTest

import com.example.test_20221108.AbstractTest
import com.example.test_20221108.vv1.TraceRecorderVV1
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class TraceRecorderV1Tests @Autowired constructor(
    val traceRecorderV1: TraceRecorderVV1
): AbstractTest() {
    @Test
    fun `begin end`() {
        val status = traceRecorderV1.begin("hello")
        Thread.sleep(400)
        traceRecorderV1.end(status)
    }
}