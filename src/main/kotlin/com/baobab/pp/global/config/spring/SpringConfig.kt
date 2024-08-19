package com.baobab.pp.global.config.spring

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig {
    @Bean
    fun objectMapper() = ObjectMapper()
}