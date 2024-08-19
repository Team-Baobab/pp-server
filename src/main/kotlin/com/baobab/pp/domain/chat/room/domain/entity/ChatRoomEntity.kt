package com.baobab.pp.domain.chat.room.domain.entity

import com.baobab.pp.domain.user.domain.entity.UserEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "chat_rooms")
class ChatRoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    val user1: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false)
    val user2: UserEntity
)