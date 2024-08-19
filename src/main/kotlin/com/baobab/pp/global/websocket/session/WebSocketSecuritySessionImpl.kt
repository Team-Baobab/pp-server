package com.baobab.pp.global.websocket.session

import com.baobab.pp.domain.user.dto.User
import com.baobab.pp.domain.user.error.UserError
import com.baobab.pp.domain.user.mapper.UserMapper
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.error.CustomException
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.stereotype.Component

@Component
class WebSocketSecuritySessionImpl(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository
) : WebSocketSecuritySession {
    override val user: User
        get() = userMapper.toDomain(userRepository.findByUsername(SimpAttributesContextHolder.currentAttributes().getAttribute("username") as String? ?: throw CustomException(UserError.USER_NOT_FOUND)) ?: throw CustomException(UserError.USER_NOT_FOUND))
}