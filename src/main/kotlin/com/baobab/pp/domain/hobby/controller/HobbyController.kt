package com.baobab.pp.domain.hobby.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hobbies")
class HobbyController {
    @GetMapping("/songs")
    fun getSongs(@RequestParam query: String) {

    }
}