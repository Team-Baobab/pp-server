package com.baobab.pp.domain.auth.repository

interface RefreshTokenRepository {
    fun save(email: String, refreshToken: String)
    fun findByUsername(username: String): String?
    fun deleteByUsername(username: String)
    fun existsByUsername(username: String): Boolean
}