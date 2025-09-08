package com.mohdiop.finkuprestapi.service

import com.mohdiop.finkuprestapi.dto.request.CreateFinkRequest
import com.mohdiop.finkuprestapi.dto.request.UpdateFinkRequest
import com.mohdiop.finkuprestapi.dto.response.FinkResponse
import com.mohdiop.finkuprestapi.dto.response.UserlessFinkResponse
import com.mohdiop.finkuprestapi.entity.finkFromRequest
import com.mohdiop.finkuprestapi.entity.toResponse
import com.mohdiop.finkuprestapi.entity.toUserlessResponse
import com.mohdiop.finkuprestapi.repository.FinkRepository
import com.mohdiop.finkuprestapi.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FinkService(
    private val finkRepository: FinkRepository,
    private val userRepository: UserRepository
) {
    fun createFink(createFinkRequest: CreateFinkRequest): UserlessFinkResponse {
        val userId =
            Integer.valueOf(SecurityContextHolder.getContext().authentication.principal.toString())
                .toLong()
        val user = getUserOrThrowException(userId)
        val finkToSave = finkFromRequest(createFinkRequest)
        finkToSave.finkUser = user
        return finkRepository.save(finkToSave).toUserlessResponse()
    }

    fun createFinks(
        userId: Long,
        createFinkRequests: List<CreateFinkRequest>
    ): List<UserlessFinkResponse> {
        if (createFinkRequests.isEmpty()) {
            return emptyList()
        }
        val user = getUserOrThrowException(userId)
        val finksToSave = createFinkRequests.map { requestFink -> finkFromRequest(requestFink) }
        finksToSave.forEach { it.finkUser = user }
        return finkRepository.saveAll(finksToSave)
            .map { fink -> fink.toUserlessResponse() }
    }

    fun updateFink(
        demanderId: Long,
        finkId: Long,
        updateFinkRequest: UpdateFinkRequest
    ): UserlessFinkResponse {
        val demander = getUserOrThrowException(demanderId)
        val finkToUpdate = getFinkOrThrowException(finkId)
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
        updateFinkRequest.category
            ?.takeIf { it != finkToUpdate.finkCategory }
            ?.let { newCategory ->
                finkToUpdate.finkCategory = newCategory
            }
        if (updateFinkRequest.title != null || updateFinkRequest.content != null) {
            finkToUpdate.finkLastUpdatedAt = LocalDateTime.now()
        }
        return finkRepository.save(
            finkToUpdate
        )
            .toUserlessResponse()
    }

    fun deleteFink(demanderId: Long, finkId: Long) {
        getFinkOrThrowException(finkId)
            .takeIf { it.finkUser.userId == demanderId }
            .let {
                return@let if (it != null)
                    finkRepository.deleteById(finkId)
                else
                    throw AccessDeniedException("Fink non supprimable par cet utilisateur.")
            }
    }

    fun getFinkById(demanderId: Long, finkId: Long): UserlessFinkResponse {
        return getFinkOrThrowException(finkId)
            .takeIf { it.finkUser.userId == demanderId }
            .let {
                return@let it?.toUserlessResponse()
                    ?: throw AccessDeniedException("Fink non affichable par cet utilisateur.")
            }
    }

    fun getFinksByUserId(userId: Long): List<UserlessFinkResponse> {
        getUserOrThrowException(userId)
        return finkRepository.findByFinkUserUserId(userId)
            .map { fink -> fink.toUserlessResponse() }
    }

    fun getAllFinks(): List<FinkResponse> {
        return finkRepository.findAll()
            .map { fink -> fink.toResponse() }
    }

    private fun getUserOrThrowException(userId: Long) =
        userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("Utilisateur introuvable.") }

    private fun getFinkOrThrowException(finkId: Long) =
        finkRepository.findById(finkId)
            .orElseThrow { EntityNotFoundException("Fink introuvable.") }
}