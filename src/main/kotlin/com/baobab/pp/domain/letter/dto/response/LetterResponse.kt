package com.baobab.pp.domain.letter.dto.response

import com.baobab.pp.domain.letter.domain.entity.LetterEntity
import java.time.LocalDateTime

data class LetterResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun of(letter: LetterEntity) = LetterResponse(
            id = letter.id!!,
            title = letter.title,
            content = letter.content,
            createdAt = letter.createdAt
        )
    }
}