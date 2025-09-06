package com.mohdiop.finkuprestapi.controller

import com.mohdiop.finkuprestapi.dto.request.CreateUserRequest
import com.mohdiop.finkuprestapi.dto.request.UpdateUserRequest
import com.mohdiop.finkuprestapi.dto.response.UserResponse
import com.mohdiop.finkuprestapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(
        @Validated @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity(
            userService.createUser(createUserRequest),
            HttpStatus.CREATED
        )
    }

    @PatchMapping("/{userId}")
    fun updateUser(
        @PathVariable userId: Long,
        @Validated @RequestBody updateUserRequest: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.updateUser(userId, updateUserRequest))
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable userId: Long
    ): ResponseEntity<Unit> {
        userService.deleteUserById(userId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{userId}")
    fun getUserById(
        @PathVariable userId: Long
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUserById(userId))
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

}