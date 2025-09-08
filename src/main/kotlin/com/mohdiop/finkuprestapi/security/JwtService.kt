package com.mohdiop.finkuprestapi.security

import com.mohdiop.finkuprestapi.entity.enum.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date
import kotlin.io.encoding.Base64

@Service
class JwtService(
    @param:Value("\${jwt.secret}") private val jwtSecret: String
) {
    private val secretKey = Keys.hmacShaKeyFor(Base64.decode(jwtSecret.toByteArray()))
    val accessTokenValidityMs = 15L * 60L * 1000L
    val refreshTokenValidityMs = 30L * 24L * 60L * 60L * 1000L

    private enum class TokenType {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }

    private fun generateToken(
        userId: Long,
        tokenType: TokenType,
        expiryAt: Long,
        userRoles: MutableSet<Role>
    ): String {
        val now = Date.from(Instant.now())
        val expirationDate = Date(now.time + expiryAt)
        return Jwts.builder()
            .subject(userId.toString())
            .claim("type", tokenType.name)
            .claim("roles", userRoles.map { it.name })
            .issuedAt(now)
            .expiration(expirationDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateAccessToken(userId: Long, userRoles: MutableSet<Role>) =
        generateToken(userId, TokenType.ACCESS_TOKEN, accessTokenValidityMs, userRoles)

    fun generateRefreshToken(userId: Long, userRoles: MutableSet<Role>) =
        generateToken(userId, TokenType.REFRESH_TOKEN, refreshTokenValidityMs, userRoles)

    fun isValidAccessToken(accessToken: String): Boolean {
        val claims = parseAllClaims(accessToken) ?: return false
        val stringTokenType = claims["type"] as? String ?: return false
        return TokenType.valueOf(stringTokenType) == TokenType.ACCESS_TOKEN
    }

    fun isValidRefreshToken(refreshToken: String): Boolean {
        val claims = parseAllClaims(refreshToken) ?: return false
        val stringTokenType = claims["type"] as? String ?: return false
        return TokenType.valueOf(stringTokenType) == TokenType.REFRESH_TOKEN
    }

    fun getUserIdFromToken(token: String): Long {
        val claims = parseAllClaims(token) ?: throw IllegalArgumentException("Token invalide.")
        return claims.subject.toLong()
    }

    fun getUserRolesFromToken(token: String): MutableSet<Role> {
        val claims = parseAllClaims(token) ?: throw IllegalArgumentException("Token invalide")
        return (claims["roles"] as List<*>).map { Role.valueOf(it.toString()) }.toMutableSet()
    }

    private fun parseAllClaims(token: String): Claims? {
        val rawToken = if (token.startsWith("Bearer "))
            token.removePrefix("Bearer ")
        else token
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(rawToken)
            .payload
    }
}