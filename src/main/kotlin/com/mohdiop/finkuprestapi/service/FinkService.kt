package com.mohdiop.finkuprestapi.service

import com.mohdiop.finkuprestapi.dto.request.CreateFinkRequest
import com.mohdiop.finkuprestapi.dto.response.FinkResponse
import com.mohdiop.finkuprestapi.dto.request.UpdateFinkRequest
import com.mohdiop.finkuprestapi.dto.response.UserFinksResponse
import com.mohdiop.finkuprestapi.entity.finkFromRequest
import com.mohdiop.finkuprestapi.entity.toResponse
import com.mohdiop.finkuprestapi.entity.toUserResponse
import com.mohdiop.finkuprestapi.repository.FinkRepository
import com.mohdiop.finkuprestapi.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.nio.file.AccessDeniedException
import java.time.LocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

@Service
class FinkService(
    private val finkRepository: FinkRepository,
    private val userRepository: UserRepository
) {
    fun createFink(userId: Long, createFinkRequest: CreateFinkRequest): FinkResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("Utilisateur introuvable!") }
        val finkToSave = finkFromRequest(createFinkRequest)
        finkToSave.finkUser = user
        return finkRepository.save(finkToSave).toResponse()
    }

    fun createFinks(userId: Long, createFinkRequests: List<CreateFinkRequest>): List<FinkResponse> {
        if (createFinkRequests.isEmpty()) {
            return emptyList()
        }
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("Utilisateur introuvable!") }
        val finksToSave = createFinkRequests.map { requestFink -> finkFromRequest(requestFink) }
        finksToSave.forEach { it.finkUser = user }
        return finkRepository.saveAll(finksToSave)
            .map { fink -> fink.toResponse() }
    }

    fun updateFink(
        demanderId: Long,
        finkId: Long,
        updateFinkRequest: UpdateFinkRequest
    ): FinkResponse {
        val demander = userRepository.findById(demanderId)
            .orElseThrow { EntityNotFoundException("Utilisateur introuvable!") }
        val finkToUpdate = finkRepository.findById(finkId)
            .orElseThrow { throw EntityNotFoundException("Fink introuvable!") }
        if (demander.userId != finkToUpdate.finkUser.userId) {
            throw AccessDeniedException("Fink non modifiable par cet utilisateur!")
        }
        updateFinkRequest.title
            ?.takeIf { it != finkToUpdate.finkTitle }
            ?.let { newTitle ->
                finkToUpdate.finkTitle = newTitle
            }
        updateFinkRequest.content
            ?.takeIf { it != finkToUpdate.finkContent }
            ?.let { newContent ->
                finkToUpdate.finkContent = newContent
            }
        if(updateFinkRequest.title != null || updateFinkRequest.content != null) {
            finkToUpdate.finkLastUpdatedAt = LocalDateTime.now()
        }
        return finkRepository.save(
            finkToUpdate
        )
            .toResponse()
    }

    fun deleteFink(demanderId: Long, finkId: Long) {
        getFinkOrThrowException(finkId)
            .takeIf { it.finkUser.userId == demanderId }
            .let { return@let if (it != null) finkRepository.deleteById(finkId) else throw AccessDeniedException("Fink non supprimable par cet utilisateur!") }
    }

    fun getFinkById(demanderId: Long, finkId: Long): FinkResponse {
        return getFinkOrThrowException(finkId)
            .takeIf { it.finkUser.userId == demanderId }
            .let {
                return@let it?.toResponse()
                    ?: throw AccessDeniedException("Fink non affichable par cet utilisateur!")
            }
    }

    fun getFinksByUserId(userId: Long): List<UserFinksResponse> {
        getUserOrThrowException(userId)
        return finkRepository.findByFinkUserUserId(userId)
            .map { fink -> fink.toUserResponse() }
    }

    fun getAllFinks(): List<FinkResponse> {
        return finkRepository.findAll()
            .map { fink -> fink.toResponse() }
    }

    private fun getUserOrThrowException(userId: Long) =
        userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("Utilisateur introuvable!") }

    private fun getFinkOrThrowException(finkId: Long) =
        finkRepository.findById(finkId)
            .orElseThrow { EntityNotFoundException("Fink introuvable!") }
}