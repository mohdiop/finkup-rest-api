package com.mohdiop.finkuprestapi.dto.request

import com.mohdiop.finkuprestapi.entity.enum.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateFinkRequest(
    @field:NotBlank(message = "Titre invalide.") val title: String,
    @field:NotBlank(message = "Contenu invalide.") val content: String,
    @field:NotNull(message = "Catégorie invalide.") val category: Category
)
