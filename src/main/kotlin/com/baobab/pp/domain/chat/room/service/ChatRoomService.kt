package com.baobab.pp.domain.chat.room.service

import com.baobab.pp.domain.chat.room.dto.response.ChatRoomResponse
import com.baobab.pp.domain.chat.room.dto.ChatRoom
import java.util.UUID

interface ChatRoomService {
    fun getChatRooms(): List<ChatRoomResponse>
    fun getChatRoom(roomId: UUID): ChatRoom
    fun createChatRoom(userId: Long): UUID
}