package com.baobab.pp.domain.letter.controller

import com.baobab.pp.domain.letter.dto.request.LetterCreateRequest
import com.baobab.pp.domain.letter.dto.request.LetterUpdateRequest
import com.baobab.pp.domain.letter.service.LetterService
import com.baobab.pp.global.common.response.BaseResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Letter", description = "편지")
@RestController
@RequestMapping("/letters")
class LetterController(
    private val letterService: LetterService
) {
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun getLetters() = BaseResponse(data = letterService.getLetters(), status = 200, message = "편지 목록을 가져옴")

    @GetMapping("/{letterId}")
    @PreAuthorize("isAuthenticated()")
    fun getLetter(@PathVariable letterId: Long) = BaseResponse(data = letterService.getLetter(letterId), status = 200, message = "편지를 가져옴")

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createLetter(@RequestBody request: LetterCreateRequest) = BaseResponse(data = letterService.createLetter(request), status = 200, message = "편지를 생성함")

    @PatchMapping("/{letterId}")
    @PreAuthorize("isAuthenticated()")
    fun updateLetter(@PathVariable letterId: Long, @RequestBody request: LetterUpdateRequest) = BaseResponse(data = letterService.updateLetter(letterId, request), status = 200, message = "편지를 수정함")

    @DeleteMapping("/{letterId}")
    @PreAuthorize("isAuthenticated()")
    fun deleteLetter(@PathVariable letterId: Long) = BaseResponse(data = letterService.deleteLetter(letterId), status = 200, message = "편지를 삭제함")
}