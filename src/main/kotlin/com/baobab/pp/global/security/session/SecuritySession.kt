package com.baobab.pp.global.security.session

import com.baobab.pp.domain.user.dto.User

interface SecuritySession {
    val user: User
}