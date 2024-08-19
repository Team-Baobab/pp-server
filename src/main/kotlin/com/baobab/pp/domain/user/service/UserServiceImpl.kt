package com.baobab.pp.domain.user.service

import com.baobab.pp.domain.user.dto.User
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.security.session.SecuritySession
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val securitySession: SecuritySession,
    private val userRepository: UserRepository
) : UserService {
    override fun getMe() = securitySession.user

    override fun getUsers(): List<User> {
        return userRepository.findAll().map { User.of(it) }
    }
}