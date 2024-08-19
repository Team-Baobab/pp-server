package com.baobab.pp.global.security.jwt.filter

import com.baobab.pp.global.security.jwt.provider.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : GenericFilterBean() {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        filterChain: FilterChain
    ) {
        val token = jwtProvider.extractToken(request as HttpServletRequest)

        if (token != null) {
            val authentication = jwtProvider.getAuthentication(token)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}