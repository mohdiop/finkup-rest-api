package com.mohdiop.finkuprestapi.repository

import com.mohdiop.finkuprestapi.entity.Fink
import org.springframework.data.jpa.repository.JpaRepository

interface FinkRepository : JpaRepository<Fink,Long> {
    fun findByFinkTitle(finkTitle: String): List<Fink>
    fun findByFinkContent(finkContent: String): List<Fink>
}