package com.baobab.pp.global.websocket.handler

import com.baobab.pp.domain.user.error.UserError
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.error.CustomException
import com.baobab.pp.global.security.jwt.error.JwtError
import com.baobab.pp.global.security.jwt.provider.JwtProvider
import org.springframework.context.event.EventListener
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class StompHandler(
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)!!

        when (accessor.messageType) {
            SimpMessageType.CONNECT -> {
                val token = jwtProvider.extractToken(accessor) ?: throw CustomException(JwtError.INVALID_TOKEN)
                val username = jwtProvider.getUsername(token)

                if (!userRepository.existsByUsername(username)) throw CustomException(UserError.USER_NOT_FOUND)

                SimpAttributesContextHolder.currentAttributes().setAttribute("username", username)

                return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
            }

            SimpMessageType.CONNECT_ACK,
            SimpMessageType.MESSAGE,
            SimpMessageType.SUBSCRIBE -> {
                val username = SimpAttributesContextHolder.currentAttributes().getAttribute("username") as String

                if (!userRepository.existsByUsername(username)) throw CustomException(UserError.USER_NOT_FOUND)

                return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
            }

            else -> {}
        }

        return message
    }

    @EventListener
    fun onSessionConnected(event: SessionConnectedEvent) {
        println("사용자 입장")
    }

    @EventListener
    fun onSessionDisconnect(event: SessionDisconnectEvent) {
        println("사용자 퇴장")
    }
}