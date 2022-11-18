package com.example.test_20221108

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class CoroutineContextTest {
    @Test
    fun `test coroutine context - not work ThreadLocal (Unconfined)`() {
        /*
            uncoenfined는 코루틴이 여러개의 스레드를 사용
         */
        runBlocking {
            val threadLocal = ThreadLocal.withInitial { "[Main]" }

            launch(Dispatchers.IO) {
                val currentThreadName = Thread.currentThread().name
                println("[launch1]thread name: $currentThreadName")

                println("[launch1]threadLocal value before set= ${threadLocal.get()}")
                threadLocal.set("[Launch1]")
                delay(1000)
                println("[launch1]threadLocal value after set= ${threadLocal.get()}")
            }

            launch(Dispatchers.Unconfined) {
                val currentThreadName = Thread.currentThread().name
                println("[launch2]thread name: $currentThreadName")

                println("[launch2]threadLocal value before set= ${threadLocal.get()}")
                threadLocal.set("[Launch2]")
                delay(1000)
                println("[launch2]threadLocal value after set= ${threadLocal.get()}")
            }
        }
    }
}