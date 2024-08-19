package com.baobab.pp.domain.chat.room.service

import com.baobab.pp.domain.chat.room.dto.response.ChatRoomResponse
import com.baobab.pp.domain.chat.room.dto.response.ChatRoomUserResponse
import com.baobab.pp.domain.chat.room.error.ChatRoomError
import com.baobab.pp.domain.chat.room.mapper.ChatRoomMapper
import com.baobab.pp.domain.chat.room.domain.entity.ChatRoomEntity
import com.baobab.pp.domain.chat.room.dto.ChatRoom
import com.baobab.pp.domain.chat.room.repository.ChatRoomRepository
import com.baobab.pp.domain.user.error.UserError
import com.baobab.pp.domain.user.mapper.UserMapper
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.error.CustomException
import com.baobab.pp.global.security.session.SecuritySession
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ChatRoomServiceImpl(
    private val userMapper: UserMapper,
    private val securitySession: SecuritySession,
    private val userRepository: UserRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRoomMapper: ChatRoomMapper
): ChatRoomService {
    override fun getChatRooms(): List<ChatRoomResponse> {
        val user = userMapper.toEntity(securitySession.user)

        val rooms = chatRoomRepository.findAllByUser1OrUser2(user, user)

        return rooms.map { chatRoomMapper.toDomain(it) }.map {
            val recipientId = if (it.user1 != user.id) it.user1 else it.user2
            val recipient = userRepository.findByIdOrNull(recipientId) ?: throw CustomException(UserError.USER_NOT_FOUND)
            val recipientDto = ChatRoomUserResponse(
                id = recipient.id!!,
                username = recipient.username,
            )

            ChatRoomResponse(
                id =  it.id,
                recipient = recipientDto,
                lastMessage = it.lastMessage
            )
        }
    }

    override fun getChatRoom(roomId: UUID): ChatRoom {
        val user = userMapper.toEntity(securitySession.user)
        val room = chatRoomRepository.findByIdOrNull(roomId) ?: throw CustomException(ChatRoomError.CHAT_ROOM_NOT_FOUND)

        if (room.user1.id != user.id && room.user2.id != user.id) throw CustomException(ChatRoomError.CHAT_ROOM_NOT_ALLOWED)

        return chatRoomMapper.toDomain(room)
    }

    override fun createChatRoom(targetId: Long): UUID {
        val user = userMapper.toEntity(securitySession.user)
        val target = userRepository.findByIdOrNull(targetId) ?: throw CustomException(UserError.USER_NOT_FOUND)

        val room1 = chatRoomRepository.findByUser1AndUser2(user, target)

        if (room1 != null) {
            return room1.id!!
        }

        val room2 = chatRoomRepository.findByUser1AndUser2(target, user)

        if (room2 != null) {
            return room2.id!!
        }

        val room = chatRoomRepository.save(
            ChatRoomEntity(
                user1 = user,
                user2 = target
            )
        )

        return room.id!!
    }
}