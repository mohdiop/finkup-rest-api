package com.mohdiop.finkuprestapi.dto.response

import java.time.LocalDateTime

data class FinkResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val lastUpdatedAt: LocalDateTime,
    val user: UserResponse
)