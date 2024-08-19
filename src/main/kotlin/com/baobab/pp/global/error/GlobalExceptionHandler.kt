package com.baobab.pp.global.error

import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException) = ErrorResponse(exception).toEntity()

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(exception: NoHandlerFoundException) = ErrorResponse(CustomException(GlobalError.NO_HANDLER_FOUND, exception.requestURL)).toEntity()

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleHttpMediaTypeNotSupportedException(exception: HttpMediaTypeNotSupportedException) = ErrorResponse(CustomException(
        GlobalError.HTTP_MEDIA_TYPE_NOT_SUPPORTED,
        exception.contentType,
        exception.supportedMediaTypes.takeIf { it.isNotEmpty() }?.joinToString("', '") ?: "N/A")
    ).toEntity()

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException) = ErrorResponse(CustomException(GlobalError.HTTP_MESSAGE_NOT_READABLE)).toEntity()

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException) = ErrorResponse(CustomException(
        GlobalError.HTTP_REQUEST_METHOD_NOT_SUPPORTED,
        exception.method,
        exception.supportedMethods?.joinToString("', '") ?: "N/A")
    ).toEntity()

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException) = ErrorResponse(CustomException(
        GlobalError.METHOD_ARGUMENT_NOT_VALID,
        exception.bindingResult.allErrors.joinToString { (it as FieldError).field })
    ).toEntity()

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(exception: BadCredentialsException) = ErrorResponse(CustomException(GlobalError.BAD_CREDENTIALS)).toEntity()

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(exception: MissingServletRequestParameterException) = ErrorResponse(CustomException(
        GlobalError.MISSING_SERVLET_REQUEST_PARAMETER,
        exception.parameterName,
        exception.parameterType)
    ).toEntity()
}