package com.mohdiop.finkuprestapi.controller

import com.mohdiop.finkuprestapi.dto.request.UpdateUserRequest
import com.mohdiop.finkuprestapi.dto.response.UserResponse
import com.mohdiop.finkuprestapi.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/me")
    fun getCurrentUserInfo(): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUserById(userService.getUserIdFromSecurityContext()))
    }

    @PatchMapping
    fun updateUser(
        @Validated @RequestBody updateUserRequest: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(
            userService.updateUser(
                userService.getUserIdFromSecurityContext(),
                updateUserRequest
            )
        )
    }

    @DeleteMapping
    fun deleteUser(): ResponseEntity<Unit> {
        userService.deleteUserById(userService.getUserIdFromSecurityContext())
        return ResponseEntity.noContent().build()
    }

}