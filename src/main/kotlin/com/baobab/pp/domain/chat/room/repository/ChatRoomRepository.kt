package com.baobab.pp.domain.chat.room.repository

import com.baobab.pp.domain.chat.room.domain.entity.ChatRoomEntity
import com.baobab.pp.domain.user.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.security.spec.ECFieldF2m
import java.util.*

interface ChatRoomRepository : JpaRepository<ChatRoomEntity, UUID> {
    fun findAllByUser1OrUser2(user1: UserEntity, user2: UserEntity): List<ChatRoomEntity>
    fun findAllByUser1AndUser2(user1: UserEntity, user2: UserEntity): List<ChatRoomEntity>
    fun findByUser1AndUser2(user1: UserEntity, user2: UserEntity): ChatRoomEntity?
}