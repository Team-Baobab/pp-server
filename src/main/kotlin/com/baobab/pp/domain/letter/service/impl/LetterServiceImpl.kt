package com.baobab.pp.domain.letter.service.impl

import com.baobab.pp.domain.letter.domain.entity.LetterEntity
import com.baobab.pp.domain.letter.dto.request.LetterCreateRequest
import com.baobab.pp.domain.letter.dto.request.LetterUpdateRequest
import com.baobab.pp.domain.letter.dto.response.LetterResponse
import com.baobab.pp.domain.letter.error.LetterError
import com.baobab.pp.domain.letter.repository.LetterRepository
import com.baobab.pp.domain.letter.service.LetterService
import com.baobab.pp.domain.user.mapper.UserMapper
import com.baobab.pp.global.error.CustomException
import com.baobab.pp.global.security.session.SecuritySession
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LetterServiceImpl(
    private val securitySession: SecuritySession,
    private val letterRepository: LetterRepository,
    private val userMapper: UserMapper
): LetterService {
    override fun getLetters(): List<LetterResponse> {
        return letterRepository.findAll().map { LetterResponse.of(it) }
    }

    override fun getLetter(letterId: Long): LetterResponse {
        return LetterResponse.of(letterRepository.findById(letterId).get())
    }

    override fun createLetter(request: LetterCreateRequest) {
        val user = userMapper.toEntity(securitySession.user)

        letterRepository.save(LetterEntity(
            title = request.title,
            content = request.content,
            user = user
        ))
    }

    override fun updateLetter(letterId: Long, request: LetterUpdateRequest) {
        val letter = letterRepository.findByIdOrNull(letterId) ?: throw CustomException(LetterError.LETTER_NOT_FOUND)

        letter.title = request.title ?: letter.title
        letter.content = request.content ?: letter.content
    }

    override fun deleteLetter(letterId: Long) {
        letterRepository.deleteById(letterId)
    }
}