package com.baobab.pp.domain.chat.message.repository

import com.baobab.pp.domain.chat.message.domain.entity.ChatMessageEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ChatMessageRepository : MongoRepository<ChatMessageEntity, ObjectId> {
    fun findAllByRoomId(roomId: UUID): List<ChatMessageEntity>
}