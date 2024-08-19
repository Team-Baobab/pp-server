package com.baobab.pp.global.security.jwt.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.baobab.pp.global.error.CustomException
import com.baobab.pp.global.error.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter(private val objectMapper: ObjectMapper) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (exception: CustomException) {
            response.write(exception)
        }
    }

    private fun HttpServletResponse.write(exception: CustomException) {
        status = exception.error.status.value()

        outputStream.use {
            it.write(objectMapper.writeValueAsBytes(ErrorResponse(exception)))
            it.flush()
        }
    }
}