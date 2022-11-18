package com.example.test_20221108.v0

sealed class BaseResponse(
    val code: String,
    val message: String,
    val success: Boolean,
) {
    companion object {
        fun <T> createSuccess(data: T): SuccessResponse<T>  = SuccessResponse(data)
        fun createFail(code: String, message: String): FailResponse  = FailResponse(code, message)
    }
}

class SuccessResponse<T>(
    val data: T,
): BaseResponse("SUCCESS", "", true)

class FailResponse (
    code: String,
    message: String,
): BaseResponse(code, message, false)