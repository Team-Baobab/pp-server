package com.baobab.pp.domain.auth.repository

import com.baobab.pp.global.security.jwt.config.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepositoryImpl(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: RedisTemplate<String, String>,
) : RefreshTokenRepository {
    override fun save(email: String, refreshToken: String) {
        redisTemplate.opsForValue()
            .set("refreshToken:${email}", refreshToken, jwtProperties.refreshTokenExpiration, TimeUnit.MILLISECONDS)
    }

    override fun findByUsername(username: String): String? {
        return redisTemplate.opsForValue().get("refreshToken:${username}")
    }

    override fun deleteByUsername(username: String) {
        redisTemplate.delete("refreshToken:${username}")
    }

    override fun existsByUsername(username: String): Boolean {
        return redisTemplate.hasKey("refreshToken:${username}")
    }
}