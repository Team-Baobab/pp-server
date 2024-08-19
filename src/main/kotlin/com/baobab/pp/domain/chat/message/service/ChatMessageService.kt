package com.baobab.pp.domain.chat.message.service

import com.baobab.pp.domain.chat.message.dto.request.SendMessageRequest
import com.baobab.pp.domain.chat.message.dto.response.ChatMessageResponse
import java.util.UUID

interface ChatMessageService {
    fun getChatMessages(roomId: UUID): List<ChatMessageResponse>

    fun sendChatMessage(request: SendMessageRequest): ChatMessageResponse
}