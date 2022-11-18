package com.example.test_20221108.trace

import java.util.*

class TraceId(
    val id: String = UUID.randomUUID().toString().substring(0, 8),
    val level: Int = -1, // 메서드를 호출하여 indent level이 올라가는 것을 의미
) {
    fun createNextId(): TraceId = TraceId(id, level + 1)
    fun createPreviousId(): TraceId = TraceId(id, level - 1)
    fun isFirstLevel(): Boolean = level == 0
}