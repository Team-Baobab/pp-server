package com.baobab.pp.global.config.websocket

import com.baobab.pp.global.websocket.handler.StompHandler
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.util.AntPathMatcher
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(private val stompHandler: StompHandler, private val rabbitProperties: RabbitProperties) : WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setPathMatcher(AntPathMatcher("."))
        registry.setApplicationDestinationPrefixes("/pub")
        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
            .setRelayHost(rabbitProperties.host)
            .setRelayPort(61613)
            .setClientLogin(rabbitProperties.username)
            .setClientPasscode(rabbitProperties.password)
            .setVirtualHost(rabbitProperties.virtualHost)
            .setSystemLogin(rabbitProperties.username)
            .setSystemPasscode(rabbitProperties.password)
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(stompHandler)
    }

    override fun configureWebSocketTransport(registry: WebSocketTransportRegistration) {
        registry.setMessageSizeLimit(512 * 1024) // 메시지 최대 크기를 512KB
        registry.setSendTimeLimit(10 * 1000) // 10초 이내에 메시지를 보내야 함
        registry.setSendBufferSizeLimit(512 * 1024) // 버퍼 사이즈를 512KB로 제한
    }
}