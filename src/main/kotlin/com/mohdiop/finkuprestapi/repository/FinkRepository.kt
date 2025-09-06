package com.mohdiop.finkuprestapi.repository

import com.mohdiop.finkuprestapi.entity.Fink
import org.springframework.data.jpa.repository.JpaRepository

interface FinkRepository : JpaRepository<Fink, Long> {
    fun findByFinkUserUserId(userId: Long): List<Fink>
}