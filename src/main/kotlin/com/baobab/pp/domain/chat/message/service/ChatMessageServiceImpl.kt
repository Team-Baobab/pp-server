package com.baobab.pp.domain.chat.message.service

import com.baobab.pp.domain.chat.message.domain.entity.ChatMessageEntity
import com.baobab.pp.domain.chat.message.dto.request.SendMessageRequest
import com.baobab.pp.domain.chat.message.dto.response.ChatMessageResponse
import com.baobab.pp.domain.chat.message.repository.ChatMessageRepository
import com.baobab.pp.domain.chat.room.error.ChatRoomError
import com.baobab.pp.domain.chat.room.repository.ChatRoomRepository
import com.baobab.pp.domain.user.mapper.UserMapper
import com.baobab.pp.global.error.CustomException
import com.baobab.pp.global.security.session.SecuritySession
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ChatMessageServiceImpl(
    private val securitySession: SecuritySession,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val rabbitTemplate: RabbitTemplate,
    private val objectMapper: ObjectMapper,
    private val userMapper: UserMapper
): ChatMessageService {
    override fun getChatMessages(roomId: UUID): List<ChatMessageResponse> {
        val user = userMapper.toEntity(securitySession.user)

        val room = chatRoomRepository.findByIdOrNull(roomId) ?: throw CustomException(ChatRoomError.CHAT_ROOM_NOT_FOUND)

        if (room.user1.id != user.id && room.user2.id != user.id) throw CustomException(ChatRoomError.CHAT_ROOM_NOT_ALLOWED)

        val messages = chatMessageRepository.findAllByRoomId(room.id!!)

        return messages.map { ChatMessageResponse(it.roomId, it.message, it.userId, it.createdAt!!, it.updatedAt!!) }
    }

    override fun sendChatMessage(request: SendMessageRequest): ChatMessageResponse {
        val userId = SimpAttributesContextHolder.currentAttributes().getAttribute("userId") as Long

        val message = chatMessageRepository.save(ChatMessageEntity(
            roomId = request.roomId,
            message = request.message,
            userId = userId
        ))

        val response = ChatMessageResponse(
            roomId = message.roomId,
            message = message.message,
            userId = message.userId,
            createdAt = message.createdAt!!,
            updatedAt = message.updatedAt!!
        )

        rabbitTemplate.convertAndSend("chat.exchange", "room.${response.roomId}", objectMapper.writeValueAsBytes(response))

        return response
    }
}