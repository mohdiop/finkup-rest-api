package com.mohdiop.finkuprestapi.dto.response

import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdAt: LocalDateTime
)
