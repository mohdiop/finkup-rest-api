package com.mohdiop.finkuprestapi.entity

import com.mohdiop.finkuprestapi.dto.request.CreateFinkRequest
import com.mohdiop.finkuprestapi.dto.response.FinkResponse
import com.mohdiop.finkuprestapi.dto.response.UserFinksResponse
import com.mohdiop.finkuprestapi.dto.response.UserResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

@Entity
@Table(name = "finks")
data class Fink(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var finkId: Long,
    @Column(nullable = false) var finkTitle: String,
    @Column(nullable = false) var finkContent: String,
    @Column(nullable = false) var finkCreatedAt: LocalDateTime,
    @Column(nullable = false) var finkLastUpdatedAt: LocalDateTime,
    @ManyToOne @JoinColumn(name = "fink_user_id") var finkUser: User
)

fun finkFromRequest(createFinkRequest: CreateFinkRequest): Fink {
    return Fink(
        0L,
        createFinkRequest.title,
        createFinkRequest.content,
        LocalDateTime.now(),
        LocalDateTime.now(),
        User()
    )
}

fun Fink.toResponse(): FinkResponse {
    return FinkResponse(
        this.finkId,
        this.finkTitle,
        this.finkContent,
        this.finkCreatedAt,
        this.finkLastUpdatedAt,
        UserResponse(
            this.finkUser.userId,
            this.finkUser.userEmail,
            this.finkUser.userFirstName,
            this.finkUser.userLastName,
            this.finkUser.userCreatedAt
        )
    )
}

fun Fink.toUserResponse(): UserFinksResponse {
    return UserFinksResponse(
        this.finkId,
        this.finkTitle,
        this.finkContent,
        this.finkCreatedAt,
        this.finkLastUpdatedAt,
        this.finkUser.userId
    )
}
