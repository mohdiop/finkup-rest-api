package com.mohdiop.finkuprestapi.repository

import com.mohdiop.finkuprestapi.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByUserUserIdAndToken(userId: Long, token: String): Optional<RefreshToken>
    fun findByUserUserId(userId: Long): Optional<RefreshToken>
}