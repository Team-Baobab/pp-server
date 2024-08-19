package com.baobab.pp.global.error

import org.springframework.http.HttpStatus

enum class GlobalError(override val status: HttpStatus, override val message: String) : CustomError {
    NO_HANDLER_FOUND(HttpStatus.NOT_FOUND, "요청한 url 를 찾을 수 없습니다."),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "미디어 지원되지 않습니다. (지원하는 미디어:)"),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "요청 바디가 없거나 읽을 수 없습니다."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED, "메소드 지원되지 않습니다. (지원하는 메소드)"),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "잘못된 파라미터"),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 계정 정보입니다."),
    MISSING_SERVLET_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터가 없습니다. (파라미터 타dlq)")
}