package com.baobab.pp.domain.chat.room.mapper

import com.baobab.pp.domain.chat.message.repository.ChatMessageRepository
import com.baobab.pp.domain.chat.room.domain.entity.ChatRoomEntity
import com.baobab.pp.domain.chat.room.dto.ChatRoom
import com.baobab.pp.domain.user.error.UserError
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.common.mapper.Mapper
import com.baobab.pp.global.error.CustomException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ChatRoomMapper(
    private val userRepository: UserRepository,
    private val chatMessageRepository: ChatMessageRepository
): Mapper<ChatRoom, ChatRoomEntity> {
    override fun toDomain(entity: ChatRoomEntity) = ChatRoom(
        id = entity.id!!,
        user1 = entity.user1.id!!,
        user2 = entity.user2.id!!,
        lastMessage = chatMessageRepository.findAllByRoomId(entity.id).takeIf { it.isNotEmpty() }?.sortedWith { o1, o2 -> o1.createdAt!!.compareTo(o2.createdAt) }
            ?.last()?.message
            ?: ""
    )

    override fun toEntity(domain: ChatRoom) = ChatRoomEntity(
        id = domain.id,
        user1 = userRepository.findByIdOrNull(domain.user1) ?: throw CustomException(UserError.USER_NOT_FOUND),
        user2 = userRepository.findByIdOrNull(domain.user2) ?: throw CustomException(UserError.USER_NOT_FOUND)
    )
}