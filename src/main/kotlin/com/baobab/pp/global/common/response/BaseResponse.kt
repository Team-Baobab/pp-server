package com.baobab.pp.global.common.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse<T>(
    val status: Int,
    val success: Boolean = true,
    val message: String = "",
    val data: T
)