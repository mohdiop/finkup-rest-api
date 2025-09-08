package com.mohdiop.finkuprestapi.component

import com.mohdiop.finkuprestapi.entity.User
import com.mohdiop.finkuprestapi.entity.enum.Role
import com.mohdiop.finkuprestapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class AdminInitializer(
    @param:Value("\${admin.email}") private val adminEmail: String,
    @param:Value("\${admin.password}") private val adminPassword: String,
    @param:Value("\${admin.firstname}") private val adminFirstName: String,
    @param:Value("\${admin.lastName}") private val adminLastName: String,
    private val userRepository: UserRepository
) : CommandLineRunner {

    @Throws(IllegalArgumentException::class)
    override fun run(vararg args: String?) {
        val user = userRepository.findUserByUserEmail(adminEmail)
        if (user.isPresent) {
            if (!user.get().userRoles.contains(Role.ROLE_ADMIN)) {
                throw IllegalArgumentException("Email invalide pour administration.")
            }
            return
        }
        userRepository.save(
            User(
                userEmail = adminEmail,
                userPassword = BCrypt.hashpw(adminPassword, BCrypt.gensalt()),
                userRoles = mutableSetOf(Role.ROLE_USER, Role.ROLE_ADMIN),
                userFirstName = adminFirstName,
                userLastName = adminLastName
            )
        )
    }
}