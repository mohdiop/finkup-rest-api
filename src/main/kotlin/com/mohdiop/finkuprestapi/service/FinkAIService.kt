package com.mohdiop.finkuprestapi.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mohdiop.finkuprestapi.entity.enum.Category
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class FinkAIService(
    @param:Value("\${fink-ai.max-tokens}") private val maxTokens: Long,
    @param:Value("\${fink-ai.model}") private val model: String,
    @param:Value("\${fink-ai.pre-prompt}") private val prePrompt: String,
    private val finkAiClient: WebClient
) {

    enum class TreatmentType {
        RESUME,
        DEVELOPMENT
    }

    fun treatFink(
        finkContent: String,
        finkCategory: Category,
        treatmentType: TreatmentType
    ): String? {
        val jsonBody = finkAiClient.post()
            .bodyValue(
                mapOf(
                    "model" to model,
                    "max_tokens" to maxTokens,
                    "messages" to listOf(
                        mapOf(
                            "role" to "system",
                            "content" to prePrompt
                        ),
                        mapOf(
                            "role" to "user",
                            "content" to "{${finkCategory.name}, $finkContent,${
                                when (treatmentType) {
                                    TreatmentType.RESUME -> "Résumé"
                                    TreatmentType.DEVELOPMENT -> "Développement"
                                }
                            }"
                        )
                    )
                )
            )
            .retrieve()
            .onStatus({ it.is4xxClientError }) { response ->
                response.bodyToMono(String::class.java)
                    .flatMap { Mono.error(RuntimeException("Erreur client: $it")) }
            }
            .onStatus({ it.is5xxServerError }) { response ->
                response.bodyToMono(String::class.java)
                    .flatMap { Mono.error(RuntimeException("Erreur serveur: $it")) }
            }
            .bodyToMono(String::class.java)
            .block()
        return extractContent(jsonBody)
    }

    fun extractContent(json: String?): String? {
        val mapper = jacksonObjectMapper()
        val root: JsonNode = mapper.readTree(json)
        return root.path("choices")
            .get(0)
            .path("message")
            .path("content")
            .asText(null)
    }
}