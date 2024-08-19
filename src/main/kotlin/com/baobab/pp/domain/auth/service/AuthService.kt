package com.baobab.pp.domain.auth.service

import com.baobab.pp.domain.auth.dto.request.LoginRequest
import com.baobab.pp.domain.auth.dto.request.ReissueRequest
import com.baobab.pp.domain.auth.dto.request.SignUpRequest
import com.baobab.pp.global.security.jwt.dto.Jwt

interface AuthService {
    fun login(request: LoginRequest): Jwt
    fun signup(request: SignUpRequest)
    fun reissue(request: ReissueRequest): Jwt
}