package com.mohdiop.finkuprestapi.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiController(
    @param:Value("\${api.version}") private val apiVersion: String
){
    @GetMapping("/info")
    fun getApiInformation(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "name" to "FinkUp API",
                "version" to apiVersion
            )
        )
    }
}