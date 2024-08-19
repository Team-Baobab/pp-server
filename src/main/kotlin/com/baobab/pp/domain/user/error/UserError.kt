package com.baobab.pp.domain.user.error

import com.baobab.pp.global.error.CustomError
import org.springframework.http.HttpStatus

enum class UserError(override val status: HttpStatus, override val message: String) : CustomError {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USERNAME_DUPLICATION(HttpStatus.BAD_REQUEST, "유저네임이 이미 있음"),
    PHONE_DUPLICATION(HttpStatus.BAD_REQUEST, "폰이 이미 있음.")
}