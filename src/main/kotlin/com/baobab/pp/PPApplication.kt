package com.baobab.pp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class PpApplication

fun main(args: Array<String>) {
    runApplication<PpApplication>(*args)
}
