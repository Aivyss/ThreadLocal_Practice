package com.example.test_20221108.v0

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV0 @Autowired constructor(
    private val orderServiceV0: OrderServiceV0,
) {

    @GetMapping("/v0/request")
    fun request(itemId: String?): SuccessResponse<String> {
        orderServiceV0.orderItem(itemId ?: "")

        return BaseResponse.createSuccess("ok")
    }
}