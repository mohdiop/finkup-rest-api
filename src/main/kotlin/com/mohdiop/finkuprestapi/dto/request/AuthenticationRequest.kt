package com.mohdiop.finkuprestapi.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest (
    @field:Email(message = "Email invalide!") val email: String,
    @field:NotBlank(message = "Mot de passe obligatoire!") val password: String
)