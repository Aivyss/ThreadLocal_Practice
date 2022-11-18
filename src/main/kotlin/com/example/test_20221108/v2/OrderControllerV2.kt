package com.example.test_20221108.v2

import com.example.test_20221108.trace.TraceRecorderV2
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
class OrderControllerV2 @Autowired constructor(
    private val orderServiceV2: OrderServiceV2,
    private val traceRecorderV2: TraceRecorderV2,
) {

    @GetMapping("/v2/request")
    fun request(itemId: String?): SuccessResponse<String> {
        val status = traceRecorderV2.begin(message="OrderControllerV1.request()")

        try {
            orderServiceV2.orderItem(itemId ?: "", status.traceId)
            traceRecorderV2.end(status)
        } catch(e: Exception) {
            traceRecorderV2.exception(status, e)

            throw e // 기존의 흐름에 영향을 미치지 않기 위해 다시 throw
        }

        return BaseResponse.createSuccess("ok")
    }
}