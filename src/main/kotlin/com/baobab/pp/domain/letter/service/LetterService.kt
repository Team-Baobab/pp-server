package com.baobab.pp.domain.letter.service

import com.baobab.pp.domain.letter.dto.request.LetterCreateRequest
import com.baobab.pp.domain.letter.dto.request.LetterUpdateRequest
import com.baobab.pp.domain.letter.dto.response.LetterResponse

interface LetterService {
    fun getLetters(): List<LetterResponse>
    fun getLetter(letterId: Long): LetterResponse
    fun createLetter(request: LetterCreateRequest)
    fun updateLetter(letterId: Long, request: LetterUpdateRequest)
    fun deleteLetter(letterId: Long)
}