package com.baobab.pp.domain.chat.message.controller

import com.baobab.pp.domain.chat.message.dto.request.SendMessageRequest
import com.baobab.pp.domain.chat.message.service.ChatMessageService
import com.baobab.pp.global.common.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Tag(name = "Chat Message", description = "채팅 메시지")
@RestController
@RequestMapping("/chat/messages")
class ChatMessageController(
    private val chatMessageService: ChatMessageService
) {
    @Operation(summary = "채팅 가져오기")
    @GetMapping
    fun getMessages(@RequestParam roomId: UUID) = BaseResponse(data = chatMessageService.getChatMessages(roomId), status = 200)

    @MessageMapping("chat.sendMessage")
    fun sendMessage(@Payload @Valid request: SendMessageRequest) {
        chatMessageService.sendChatMessage(request)
    }
}