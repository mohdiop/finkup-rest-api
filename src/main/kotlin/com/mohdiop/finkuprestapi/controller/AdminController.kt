package com.mohdiop.finkuprestapi.controller

import com.mohdiop.finkuprestapi.dto.response.FinkResponse
import com.mohdiop.finkuprestapi.dto.response.UserResponse
import com.mohdiop.finkuprestapi.service.FinkService
import com.mohdiop.finkuprestapi.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
class AdminController(
    private val userService: UserService,
    private val finkService: FinkService
) {

    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/finks")
    fun getAllFinks(): ResponseEntity<List<FinkResponse>> {
        return ResponseEntity.ok(finkService.getAllFinks())
    }
}