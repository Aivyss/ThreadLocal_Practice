package com.example.test_20221108.threadLocalV1

import com.example.test_20221108.v0.BaseResponse
import com.example.test_20221108.v0.SuccessResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * <h1>문제점</h1>
 * request id가 일치하지 않음 (request scope를 이용하면 되긴 함)
 * indent level이 적용되지 아니함
 * mvc 를 수행하는 빈들에 모두 traceRecorder를 넣어야함.
 */
@RestController
class OrderControllerTLV1 @Autowired constructor(
    private val orderServiceV1: OrderServiceTLV1,
    private val traceRecorderV1: TraceRecorderTLV1,
) {

    @GetMapping("/tlv1/request")
    fun request(itemId: String?): SuccessResponse<String> {
        val status = traceRecorderV1.begin("OrderControllerV1.request()")
        Thread.sleep(1500)
        try {
            orderServiceV1.orderItem(itemId ?: "")
            traceRecorderV1.end(status)
        } catch(e: Exception) {
            traceRecorderV1.exception(status, e)

            throw e // 기존의 흐름에 영향을 미치지 않기 위해 다시 throw
        }

        return BaseResponse.createSuccess("ok")
    }
}