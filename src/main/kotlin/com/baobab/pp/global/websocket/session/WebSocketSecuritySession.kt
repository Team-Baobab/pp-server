package com.baobab.pp.global.websocket.session

import com.baobab.pp.domain.user.dto.User

interface WebSocketSecuritySession {
    val user: User
}