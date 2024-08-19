package com.baobab.pp.domain.chat.message.domain.entity

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "chat_messages")
class ChatMessageEntity(
    @Id
    val id: ObjectId? = null,

    val roomId: UUID,
    val message: String = "",
    val userId: Long,

    @CreatedDate
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
)