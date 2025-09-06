package com.mohdiop.finkuprestapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Instant
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id val userId: Long? = null,
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    val user: User,
    @Column(nullable = false) val createdAt: Instant = Instant.now(),
    @Column(nullable = false) var expiresAt: Instant,
    @Column(nullable = false) var token: String
)