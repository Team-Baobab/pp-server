package com.baobab.pp.domain.auth.service

import com.baobab.pp.domain.auth.dto.request.LoginRequest
import com.baobab.pp.domain.auth.dto.request.ReissueRequest
import com.baobab.pp.domain.auth.dto.request.SignUpRequest
import com.baobab.pp.domain.auth.repository.RefreshTokenRepository
import com.baobab.pp.domain.user.domain.entity.UserEntity
import com.baobab.pp.domain.user.error.UserError
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.error.CustomException
import com.baobab.pp.global.security.jwt.dto.Jwt
import com.baobab.pp.global.security.jwt.enums.JwtType
import com.baobab.pp.global.security.jwt.error.JwtError
import com.baobab.pp.global.security.jwt.provider.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository
) : AuthService {
    override fun login(request: LoginRequest): Jwt {
        val user = userRepository.findByUsername(request.username) ?: throw CustomException(UserError.USER_NOT_FOUND)

        if (!passwordEncoder.matches(request.password, user.password))
            throw CustomException(UserError.PASSWORD_NOT_MATCH)

        val token = jwtProvider.generateToken(user)

        refreshTokenRepository.save(user.username, token.refreshToken)

        return token
    }

    override fun signup(request: SignUpRequest) {
        if (userRepository.existsByUsername(request.username))
            throw CustomException(UserError.USERNAME_DUPLICATION)
        if (userRepository.existsByPhone(request.phone))
            throw CustomException(UserError.PHONE_DUPLICATION)

        val user = UserEntity(
            username = request.username,
            phone = request.phone,
            password = passwordEncoder.encode(request.password),
        )

        userRepository.save(user)
    }

    override fun reissue(request: ReissueRequest): Jwt {
        val refreshToken = request.refreshToken

        jwtProvider.validateToken(refreshToken)

        if (jwtProvider.getType(refreshToken) != JwtType.REFRESH) {
            throw CustomException(JwtError.INVALID_TOKEN_TYPE)
        }

        val username = jwtProvider.getUsername(refreshToken)
        val user = userRepository.findByUsername(username) ?: throw CustomException(UserError.USER_NOT_FOUND)

        if (!refreshTokenRepository.existsByUsername(user.username))
            throw CustomException(JwtError.REFRESH_TOKEN_NOT_FOUND)

        val previousRefreshToken = refreshTokenRepository.findByUsername(user.username)

        if (previousRefreshToken != refreshToken)
            throw CustomException(JwtError.REFRESH_TOKEN_NOT_MATCH)

        val token = jwtProvider.generateToken(user)

        refreshTokenRepository.save(user.username, token.refreshToken)

        return token
    }
}