package com.mohdiop.finkuprestapi.controller

import com.mohdiop.finkuprestapi.dto.request.AuthenticationRequest
import com.mohdiop.finkuprestapi.dto.request.CreateUserRequest
import com.mohdiop.finkuprestapi.dto.response.UserResponse
import com.mohdiop.finkuprestapi.service.AuthenticationService
import com.mohdiop.finkuprestapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    @PostMapping("/register")
    fun createUser(
        @Validated @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity(
            userService.createUser(createUserRequest),
            HttpStatus.CREATED
        )
    }

    @PostMapping("/login")
    fun authenticateUser(
        @Validated @RequestBody authenticationRequest: AuthenticationRequest
    ): ResponseEntity<AuthenticationService.TokenPairResponse> {
        return ResponseEntity
            .ok(authenticationService.authenticate(authenticationRequest))
    }

    @PostMapping("/refresh")
    fun refreshTokens(
        @RequestBody refreshToken: String
    ): ResponseEntity<AuthenticationService.TokenPairResponse> {
        return ResponseEntity
            .ok(authenticationService.refresh(refreshToken))
    }
}