package com.mohdiop.finkuprestapi.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateUserRequest(
    @field:Email("Email invalide! Exemple: exemple@exemple.com")
    val email: String,
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$",
        message = "Mot de passe invalide (min 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial)"
    ) val password: String,
    @field:NotBlank(message = "Prénom obligatoire et ne peut pas être vide!")
    @field:Pattern(
        regexp = "^[A-ZÀ-ÖØ-Ý][a-zà-öø-ÿ]+(?:[-'][A-ZÀ-ÖØ-Ý][a-zà-öø-ÿ]+)*$",
        message = "Prénom invalide! Doit commencer par une majuscule et ne contenir que des lettres, tirets ou apostrophes"
    )
    val firstName: String,
    @field:NotBlank(message = "Nom obligatoire et ne peut pas être vide!")
    @field:Pattern(
        regexp = "^[A-ZÀ-ÖØ-Ý][a-zà-öø-ÿ]+(?:[-'][A-ZÀ-ÖØ-Ý][a-zà-öø-ÿ]+)*$",
        message = "Prénom invalide! Doit commencer par une majuscule et ne contenir que des lettres, tirets ou apostrophes"
    )
    val lastName: String
)
