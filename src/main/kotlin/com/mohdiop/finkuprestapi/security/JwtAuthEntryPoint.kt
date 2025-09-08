package com.mohdiop.finkuprestapi.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {

    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"

        val message = when (authException) {
            is BadCredentialsException -> "Email ou mot de passe incorrect."
            is LockedException -> "Compte verrouillé."
            is DisabledException -> "Compte désactivé."
            is AccountExpiredException -> "Compte expiré."
            is CredentialsExpiredException -> "Mot de passe expiré."
            else -> "Accès non autorisé."
        }

        response.writer.write("""{"error": "$message"}""")
    }

}
