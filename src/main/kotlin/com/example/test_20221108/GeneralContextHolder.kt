package com.example.test_20221108

import kotlinx.coroutines.asContextElement
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class GeneralContextHolder {
    private val holderMap: MutableMap<KClass<*>, ThreadLocal<Any?>> = mutableMapOf()

    fun <T : Any> get(kClass: KClass<T>): T? {
        val threadLocal = holderMap[kClass]

        if (threadLocal == null) {
            val threadLocal: ThreadLocal<Any?> = ThreadLocal()
            holderMap[kClass] = threadLocal
        }

        return threadLocal?.get() as? T?
    }

    fun <T> set(instance: T, threadLocal: ThreadLocal<Any?>) {
        val kClass = instance!!::class

        threadLocal.asContextElement(instance)
        holderMap[kClass] = threadLocal
    }

    fun <T: Any> remove(kClass: KClass<T>) =holderMap[kClass]?.remove()
}