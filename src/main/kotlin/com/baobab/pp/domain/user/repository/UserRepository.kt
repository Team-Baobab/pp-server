package com.baobab.pp.domain.user.repository

import com.baobab.pp.domain.user.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
    fun existsByPhone(phone: String): Boolean
}