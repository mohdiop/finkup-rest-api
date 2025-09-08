package com.mohdiop.finkuprestapi.service

import com.mohdiop.finkuprestapi.dto.request.AuthenticationRequest
import com.mohdiop.finkuprestapi.entity.RefreshToken
import com.mohdiop.finkuprestapi.repository.RefreshTokenRepository
import com.mohdiop.finkuprestapi.repository.UserRepository
import com.mohdiop.finkuprestapi.security.JwtService
import io.jsonwebtoken.JwtException
import jakarta.transaction.Transactional
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64

@Service
class AuthenticationService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    data class TokenPairResponse(
        val accessToken: String,
        val refreshToken: String
    )

    fun authenticate(authenticationRequest: AuthenticationRequest): TokenPairResponse {
        val userToAuthenticate = userRepository.findUserByUserEmail(authenticationRequest.email)
            .orElseThrow { BadCredentialsException("Email ou mot de passe incorrect.") }
        if (BCrypt.checkpw(authenticationRequest.password, userToAuthenticate.userPassword)) {
            val newAccessToken = jwtService.generateAccessToken(userToAuthenticate.userId, userToAuthenticate.userRoles)
            val newRefreshToken = jwtService.generateRefreshToken(userToAuthenticate.userId, userToAuthenticate.userRoles)
            storeRefreshToken(userToAuthenticate.userId, newRefreshToken)
            return TokenPairResponse(
                newAccessToken,
                newRefreshToken
            )
        }
        throw BadCredentialsException("Email ou mot de passe incorrect.")
    }

    @Transactional
    fun refresh(refreshToken: String): TokenPairResponse {
        if (!jwtService.isValidRefreshToken(refreshToken)) {
            throw JwtException("")
        }
        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId)
            .orElseThrow { JwtException("") }
        val hashedToken = hashToken(refreshToken)
        refreshTokenRepository
            .findByUserUserIdAndToken(user.userId, hashedToken)
            .orElseThrow { JwtException("") }

        val newAccessToken = jwtService.generateAccessToken(user.userId, user.userRoles)
        val newRefreshToken = jwtService.generateRefreshToken(user.userId, user.userRoles)
        storeRefreshToken(user.userId, newRefreshToken)
        return TokenPairResponse(
            newAccessToken,
            newRefreshToken
        )
    }

    private fun storeRefreshToken(userId: Long, refreshToken: String) {
        val hashedToken = hashToken(refreshToken)
        val expiryMs = jwtService.refreshTokenValidityMs
        val expiresAt = Instant.now().plusMillis(expiryMs)
        val user = userRepository.findById(userId)
            .orElseThrow { JwtException("") }
        println(userId)
        val existingRefreshToken = refreshTokenRepository.findByUserUserId(userId)
        val tokenToStore = existingRefreshToken.orElse(
            RefreshToken(
                user = user,
                expiresAt = Instant.now(),
                token = ""
            )
        )
        tokenToStore.expiresAt = expiresAt
        tokenToStore.token = hashedToken
        refreshTokenRepository.save(
            tokenToStore
        )
    }

    private fun hashToken(token: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(token.toByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}