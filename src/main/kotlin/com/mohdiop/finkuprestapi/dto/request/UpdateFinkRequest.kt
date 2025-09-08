package com.mohdiop.finkuprestapi.dto.request

import com.mohdiop.finkuprestapi.entity.enum.Category
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class UpdateFinkRequest(
    @field:Pattern(
        regexp = """\S.*""",
        message = "Titre invalide!"
    ) val title: String?,
    @field:Pattern(
        regexp = """\S.*""",
        message = "Titre invalide!"
    ) val content: String?,
    @field:NotNull val category: Category?
)