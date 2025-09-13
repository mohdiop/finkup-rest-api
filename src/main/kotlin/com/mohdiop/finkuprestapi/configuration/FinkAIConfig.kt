package com.mohdiop.finkuprestapi.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class FinkAIConfig(
    @param:Value("\${fink-ai.url}") private val url: String,
    @param:Value("\${fink-ai.api-key}") private val apiKey: String
) {

    @Bean
    fun finkAiClient() =
        WebClient.builder()
            .baseUrl(url)
            .defaultHeader("Authorization", "Bearer $apiKey")
            .build()
}