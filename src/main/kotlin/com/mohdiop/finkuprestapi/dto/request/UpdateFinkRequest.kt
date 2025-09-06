package com.mohdiop.finkuprestapi.dto.request

import jakarta.validation.constraints.Pattern

data class UpdateFinkRequest(
    @field:Pattern(
        regexp = """\S.*""",
        message = "Titre invalide!"
    ) val title: String?,
    @field:Pattern(
        regexp = """\S.*""",
        message = "Titre invalide!"
    ) val content: String?
)