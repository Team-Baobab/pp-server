package com.baobab.pp.domain.user.service

import com.baobab.pp.domain.user.dto.User

interface UserService {
    fun getMe(): User

    fun getUsers(): List<User>
}