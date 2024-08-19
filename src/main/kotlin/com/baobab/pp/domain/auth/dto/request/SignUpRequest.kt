package com.baobab.pp.domain.auth.dto.request

data class SignUpRequest(
    val username: String,
    val phone: String,
    val password: String,
)