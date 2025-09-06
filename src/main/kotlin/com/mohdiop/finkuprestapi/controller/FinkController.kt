package com.mohdiop.finkuprestapi.controller

import com.mohdiop.finkuprestapi.dto.request.CreateFinkRequest
import com.mohdiop.finkuprestapi.dto.response.FinkResponse
import com.mohdiop.finkuprestapi.dto.request.UpdateFinkRequest
import com.mohdiop.finkuprestapi.dto.response.UserFinksResponse
import com.mohdiop.finkuprestapi.service.FinkService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class FinkController(
    private val finkService: FinkService
) {

    @PostMapping("/{userId}/finks")
    fun addFink(
        @PathVariable userId: Long,
        @Validated @RequestBody createFinkRequest: CreateFinkRequest
    ): ResponseEntity<FinkResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(finkService.createFink(userId, createFinkRequest))
    }

    @PostMapping("/{userId}/finks/batch")
    fun addFinks(
        @PathVariable userId: Long,
        @Validated @RequestBody createFinkRequests: List<CreateFinkRequest>
    ): ResponseEntity<List<FinkResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(finkService.createFinks(userId, createFinkRequests))
    }

    @PatchMapping("/{userId}/finks/{finkId}")
    fun updateFink(
        @PathVariable userId: Long,
        @PathVariable finkId: Long,
        @Validated @RequestBody updateFinkRequest: UpdateFinkRequest
    ): ResponseEntity<FinkResponse> {
        return ResponseEntity.ok(finkService.updateFink(userId, finkId, updateFinkRequest))
    }

    @DeleteMapping("/{userId}/finks/{finkId}")
    fun deleteFink(
        @PathVariable userId: Long,
        @PathVariable finkId: Long
    ): ResponseEntity<Unit> {
        finkService.deleteFink(userId, finkId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{userId}/finks")
    fun getUserFinks(
        @PathVariable userId: Long
    ): ResponseEntity<List<UserFinksResponse>> {
        return ResponseEntity.ok(finkService.getFinksByUserId(userId))
    }

    @GetMapping("/{userId}/finks/{finkId}")
    fun getFinkById(
        @PathVariable userId: Long,
        @PathVariable finkId: Long
    ): ResponseEntity<FinkResponse> {
        return ResponseEntity.ok(finkService.getFinkById(userId, finkId))
    }

    @GetMapping("/finks")
    fun getAllFinks(): ResponseEntity<List<FinkResponse>> {
        return ResponseEntity.ok(finkService.getAllFinks())
    }

}