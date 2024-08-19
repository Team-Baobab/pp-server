package com.baobab.pp.global.security.jwt.dto

data class Jwt(
    val accessToken: String,
    val refreshToken: String
)