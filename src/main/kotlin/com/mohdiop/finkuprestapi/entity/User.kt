package com.mohdiop.finkuprestapi.entity

import com.mohdiop.finkuprestapi.dto.request.CreateUserRequest
import com.mohdiop.finkuprestapi.dto.response.UserResponse
import com.mohdiop.finkuprestapi.entity.enum.Role
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.security.crypto.bcrypt.BCrypt
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING) val userRoles: MutableSet<Role> = mutableSetOf(Role.ROLE_USER),
    @OneToMany(
        mappedBy = "finkUser",
        cascade = [CascadeType.ALL]
    ) var userFinks: List<Fink> = emptyList(),
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL]) val refreshToken: RefreshToken? = null
)

fun userFromRequest(createUserRequest: CreateUserRequest): User {
    return User(
        0L,
        createUserRequest.email,
        BCrypt.hashpw(createUserRequest.password, BCrypt.gensalt()),
        createUserRequest.firstName,
        createUserRequest.lastName,
        LocalDateTime.now(),
        mutableSetOf(Role.ROLE_USER),
        emptyList()
    )
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        this.userId,
        this.userEmail,
        this.userFirstName,
        this.userLastName,
        this.userCreatedAt,
        this.userRoles
    )
}
