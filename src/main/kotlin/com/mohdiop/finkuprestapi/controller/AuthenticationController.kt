package com.mohdiop.finkuprestapi.controller

import com.mohdiop.finkuprestapi.dto.request.AuthenticationRequest
import com.mohdiop.finkuprestapi.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping
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