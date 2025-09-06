package com.mohdiop.finkuprestapi.dto.response

import java.time.LocalDateTime

data class UserFinksResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val lastUpdatedAt: LocalDateTime,
    val userId: Long
)
