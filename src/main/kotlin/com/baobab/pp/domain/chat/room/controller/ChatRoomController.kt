package com.baobab.pp.domain.chat.room.controller

import com.baobab.pp.domain.chat.room.dto.ChatRoomCreateRequest
import com.baobab.pp.domain.chat.room.service.ChatRoomService
import com.baobab.pp.global.common.response.BaseResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "ChatRoom", description = "채팅방")
@RestController
@RequestMapping("/chat/rooms")
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {
    @PostMapping
    fun createRoom(@RequestBody request: ChatRoomCreateRequest) = BaseResponse(data = chatRoomService.createChatRoom((request.targetId)), status = 201, message = "생성 완료")

    @GetMapping
    fun getRooms() = BaseResponse(data = chatRoomService.getChatRooms(), status = 200, message = "완려")

    @GetMapping("/{roomId}")
    fun getRoom(@PathVariable roomId: UUID) = BaseResponse(data = chatRoomService.getChatRoom(roomId), status = 200, message = "완ㄹ")
}