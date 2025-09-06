package com.mohdiop.finkuprestapi.repository

import com.mohdiop.finkuprestapi.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findUserByUserEmail(userEmail: String): Optional<User>
}
