package com.baobab.pp.domain.user.dto

import com.baobab.pp.domain.user.domain.entity.UserEntity
import com.baobab.pp.domain.user.domain.enums.UserRole

data class User(
    val id: Long,
    val username: String,
    val phone: String,
    val role: UserRole
) {
    companion object {
        fun of(user: UserEntity) = User(user.id!!, user.username, user.phone, user.role)
    }
}