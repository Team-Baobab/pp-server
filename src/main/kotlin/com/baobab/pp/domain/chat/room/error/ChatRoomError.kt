package com.baobab.pp.domain.chat.room.error

import com.baobab.pp.global.error.CustomError
import org.springframework.http.HttpStatus

enum class ChatRoomError(override val status: HttpStatus, override val message: String): CustomError {
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
    CHAT_ROOM_NOT_ALLOWED(HttpStatus.FORBIDDEN, "채팅방에 접근할 수 없습니다.")
}