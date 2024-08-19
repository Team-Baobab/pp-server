package com.baobab.pp.domain.letter.repository

import com.baobab.pp.domain.letter.domain.entity.LetterEntity
import com.baobab.pp.domain.user.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LetterRepository: JpaRepository<LetterEntity, Long> {
    fun findAllByUser(user: UserEntity): List<LetterEntity>
}