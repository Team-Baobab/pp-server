package com.baobab.pp.global.error

import org.springframework.http.ResponseEntity

data class ErrorResponse(
    private val exception: CustomException
) {
    private val error = exception.error
    val code = (error as Enum<*>).name
    val message = error.message.format(exception.args)
    val status = error.status.value()

    fun toEntity() = ResponseEntity.status(error.status)
        .body(this)
}