package com.baobab.pp.domain.chat.message.dto.request

import java.util.UUID

data class SendMessageRequest(
    val roomId: UUID,
    val message: String
)