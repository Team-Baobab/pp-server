package com.baobab.pp.global.security.session

import com.baobab.pp.domain.user.dto.User
import com.baobab.pp.domain.user.error.UserError
import com.baobab.pp.domain.user.mapper.UserMapper
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.error.CustomException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecuritySessionImpl(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository
) : SecuritySession {
    override val user: User
        get() = userMapper.toDomain(
            userRepository.findByUsername(SecurityContextHolder.getContext().authentication.name) ?: throw CustomException(
                UserError.USER_NOT_FOUND
            )
        )
}