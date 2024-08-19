package com.baobab.pp.domain.chat.room.dto

import java.util.*

data class  ChatRoom(
    val id: UUID,
    val user1: Long,
    val user2: Long,
    val lastMessage: String
)