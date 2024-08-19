package com.baobab.pp.domain.letter.error

import com.baobab.pp.global.error.CustomError
import org.springframework.http.HttpStatus

enum class LetterError(override val status: HttpStatus, override val message: String): CustomError {
    LETTER_NOT_FOUND(HttpStatus.NOT_FOUND, "편지를 찾을 수 없습니다."),
}