package com.mohdiop.finkuprestapi.service

import com.mohdiop.finkuprestapi.dto.request.CreateUserRequest
import com.mohdiop.finkuprestapi.dto.request.UpdateUserRequest
import com.mohdiop.finkuprestapi.dto.response.UserResponse
import com.mohdiop.finkuprestapi.entity.toResponse
import com.mohdiop.finkuprestapi.entity.userFromRequest
import com.mohdiop.finkuprestapi.repository.UserRepository
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(createUserRequest: CreateUserRequest): UserResponse {
        if (userRepository.findUserByUserEmail(createUserRequest.email).isPresent) {
            throw EntityExistsException("Email déjà utilisé par un utilisateur.")
        }
        return userRepository.save(
            userFromRequest(createUserRequest)
        ).toResponse()
    }

    fun updateUser(userId: Long, updateUserRequest: UpdateUserRequest): UserResponse {
        val userToUpdate = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("Utilisateur introuvable.") }
        updateUserRequest.email
            ?.takeIf { it != userToUpdate.userEmail }
            ?.let { newEmail ->
                if (userRepository.findUserByUserEmail(newEmail).isPresent) {
                    throw EntityExistsException("Email déjà utilisé par un utilisateur.")
                }
                userToUpdate.userEmail = newEmail
            }
        updateUserRequest.password
            ?.takeIf { !BCrypt.checkpw(it, userToUpdate.userPassword) }
            ?.let { newPassword ->
                userToUpdate.userPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt())
            }
        updateUserRequest.firstName
            ?.takeIf { it != userToUpdate.userFirstName }
            ?.let { newFirstName ->
                userToUpdate.userFirstName = newFirstName
            }
        updateUserRequest.lastName
            ?.takeIf { it != userToUpdate.userLastName }
            ?.let { newLastName ->
                userToUpdate.userLastName = newLastName
            }
        return userRepository.save(userToUpdate)
            .toResponse()
    }

    fun deleteUserById(userId: Long) {
        if (userRepository.findById(userId).isPresent) {
            userRepository.deleteById(userId)
            return
        }
        throw EntityNotFoundException("Utilisateur introuvable.")
    }

    fun getUserById(userId: Long): UserResponse {
        return userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("Utilisateur introuvable.") }
            .toResponse()
    }

    fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll()
            .map { user -> user.toResponse() }
    }

    fun getCurrentUserInfo(): UserResponse {
        val userId = getUserIdFromSecurityContext()
        return userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("Utilisateur introuvable.") }
            .toResponse()
    }

    fun getUserIdFromSecurityContext() = Integer.valueOf(SecurityContextHolder.getContext().authentication.principal.toString()).toLong()
}