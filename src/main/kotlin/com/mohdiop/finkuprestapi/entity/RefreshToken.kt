package com.mohdiop.finkuprestapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User,
    @Column(nullable = false) val createdAt: Instant = Instant.now(),
    @Column(nullable = false) var expiresAt: Instant,
    @Column(nullable = false) var token: String
)