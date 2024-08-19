package com.baobab.pp.global.security.jwt.details

import com.baobab.pp.domain.user.error.UserError
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.error.CustomException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw CustomException(UserError.USER_NOT_FOUND)

        return JwtUserDetails(user)
    }
}