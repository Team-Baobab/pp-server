package com.baobab.pp.global.security.jwt.details

import com.baobab.pp.domain.user.domain.entity.UserEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(private val user: UserEntity) : UserDetails {
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
    override fun getUsername() = user.username
    override fun getPassword() = ""
}