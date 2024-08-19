package com.baobab.pp.domain.chat.room.dto.response

import java.util.UUID

data class ChatRoomResponse(
    val id: UUID,
    val recipient: ChatRoomUserResponse,
    val lastMessage: String
)