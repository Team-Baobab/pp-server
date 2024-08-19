package com.baobab.pp.domain.user.controller

import com.baobab.pp.domain.user.service.UserService
import com.baobab.pp.global.common.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "유저")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "내 정보 가져오기")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun getMe() = BaseResponse(data = userService.getMe(), status = 200, message = "유저를 가져옴")

    @Operation(summary = "유저 목록 가져오기")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun getUsers() = BaseResponse(data = userService.getUsers(), status = 200, message = "유저 목록을 가져옴")
}