package com.mohdiop.finkuprestapi.entity

import com.mohdiop.finkuprestapi.dto.request.CreateUserRequest
import com.mohdiop.finkuprestapi.dto.response.UserResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var userId: Long = 0L,
    @Column(nullable = false, unique = true) var userEmail: String = "",
    @Column(nullable = false) var userPassword: String = "",
    @Column(nullable = false) var userFirstName: String = "",
    @Column(nullable = false) var userLastName: String = "",
    @Column(nullable = false) var userCreatedAt: LocalDateTime = LocalDateTime.now(),
    @OneToMany(mappedBy = "finkUser") var userFinks: List<Fink> = emptyList()
)

fun userFromRequest(createUserRequest: CreateUserRequest): User {
    return User(
        0L,
        createUserRequest.email,
        BCrypt.hashpw(createUserRequest.password, BCrypt.gensalt()),
        createUserRequest.firstName,
        createUserRequest.lastName,
        LocalDateTime.now(),
        emptyList()
    )
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        this.userId,
        this.userEmail,
        this.userFirstName,
        this.userLastName,
        this.userCreatedAt
    )
}
