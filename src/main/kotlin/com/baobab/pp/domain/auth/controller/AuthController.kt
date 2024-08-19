package com.baobab.pp.domain.auth.controller

import com.baobab.pp.domain.auth.dto.request.LoginRequest
import com.baobab.pp.domain.auth.dto.request.ReissueRequest
import com.baobab.pp.domain.auth.dto.request.SignUpRequest
import com.baobab.pp.domain.auth.service.AuthService
import com.baobab.pp.global.common.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @Operation(summary = "로그인")
    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    fun login(@RequestBody request: LoginRequest) =
        BaseResponse(data = authService.login(request), status = 200, message = "로그인 성공")

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @PreAuthorize("isAnonymous()")
    fun signup(@RequestBody request: SignUpRequest) =
        BaseResponse(data = authService.signup(request), status = 200, message = "회원가입 성공")

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    @PreAuthorize("isAnonymous()")
    fun reissue(@RequestBody request: ReissueRequest) =
        BaseResponse(data = authService.reissue(request), status = 200, message = "토큰 재발급 성공")
}