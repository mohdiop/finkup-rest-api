package com.mohdiop.finkuprestapi.dto.response

import com.mohdiop.finkuprestapi.entity.enum.Role
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdAt: LocalDateTime,
    val roles: MutableSet<Role>
)
