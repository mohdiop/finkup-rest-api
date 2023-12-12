package com.mohdiop.finkuprestapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinkupRestApiApplication

fun main(args: Array<String>) {
    runApplication<FinkupRestApiApplication>(*args)
}
