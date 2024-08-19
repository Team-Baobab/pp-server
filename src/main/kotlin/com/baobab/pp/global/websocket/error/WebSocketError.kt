package com.baobab.pp.global.websocket.error

import com.baobab.pp.global.error.CustomError
import org.springframework.http.HttpStatus

class WebSocketError(override val status: HttpStatus, override val message: String): CustomError {

}