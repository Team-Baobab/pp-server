package com.baobab.pp.domain.chat.message.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class ChatMessageResponse(
    val roomId: UUID,
    val message: String,
    val userId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)