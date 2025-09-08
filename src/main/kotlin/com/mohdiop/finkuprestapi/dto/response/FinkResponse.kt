package com.mohdiop.finkuprestapi.dto.response

import com.mohdiop.finkuprestapi.entity.enum.Category
import java.time.LocalDateTime

data class FinkResponse(
    val id: Long,
    val title: String,
    val content: String,
    val category: Category,
    val createdAt: LocalDateTime,
    val lastUpdatedAt: LocalDateTime,
    val user: UserResponse
)