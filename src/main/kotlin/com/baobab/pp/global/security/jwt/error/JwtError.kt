package com.baobab.pp.global.security.jwt.error

import com.baobab.pp.global.error.CustomError
import org.springframework.http.HttpStatus

enum class JwtError(override val status: HttpStatus, override val message: String) : CustomError {
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Expired token"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "Unsupported token"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "Invalid token type"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Refresh token not found"),
    REFRESH_TOKEN_NOT_MATCH(HttpStatus.UNAUTHORIZED, "Refresh token not match"),
}