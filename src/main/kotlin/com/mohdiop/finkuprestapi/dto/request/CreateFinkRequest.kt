package com.mohdiop.finkuprestapi.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateFinkRequest(
    @field:NotBlank(message = "Titre invalide!") val title: String,
    @field:NotBlank(message = "Contenu invalide!") val content: String
)
