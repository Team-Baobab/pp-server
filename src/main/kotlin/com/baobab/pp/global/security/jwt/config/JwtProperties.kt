package com.baobab.pp.global.security.jwt.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val issuer: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
)