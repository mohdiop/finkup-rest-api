package com.mohdiop.finkuprestapi.controller

import com.mohdiop.finkuprestapi.dto.request.CreateFinkRequest
import com.mohdiop.finkuprestapi.dto.request.UpdateFinkRequest
import com.mohdiop.finkuprestapi.dto.response.UserlessFinkResponse
import com.mohdiop.finkuprestapi.service.FinkService
import com.mohdiop.finkuprestapi.service.UserService
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
    private val finkService: FinkService,
    private val userService: UserService
) {

    @PostMapping("/finks")
    fun addFink(
        @Validated @RequestBody createFinkRequest: CreateFinkRequest
    ): ResponseEntity<UserlessFinkResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(finkService.createFink(createFinkRequest))
    }

    @PostMapping("/finks/batch")
    fun addFinks(
        @Validated @RequestBody createFinkRequests: List<CreateFinkRequest>
    ): ResponseEntity<List<UserlessFinkResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                finkService.createFinks(
                    userService.getUserIdFromSecurityContext(),
                    createFinkRequests
                )
            )
    }

    @PostMapping("/finks/{finkId}/resumed")
    fun resumeFink(
        @PathVariable finkId: Long
    ): ResponseEntity<String> {
        return ResponseEntity.ok(
            finkService.resumeFink(
                userService.getUserIdFromSecurityContext(),
                finkId
            )
        )
    }

    @PostMapping("/finks/{finkId}/developed")
    fun developFink(
        @PathVariable finkId: Long
    ): ResponseEntity<String> {
        return ResponseEntity.ok(
            finkService.developFink(
                userService.getUserIdFromSecurityContext(),
                finkId
            )
        )
    }

    @PatchMapping("/finks/{finkId}")
    fun updateFink(
        @PathVariable finkId: Long,
        @Validated @RequestBody updateFinkRequest: UpdateFinkRequest
    ): ResponseEntity<UserlessFinkResponse> {
        return ResponseEntity.ok(
            finkService.updateFink(
                userService.getUserIdFromSecurityContext(),
                finkId,
                updateFinkRequest
            )
        )
    }

    @DeleteMapping("/finks/{finkId}")
    fun deleteFink(
        @PathVariable finkId: Long
    ): ResponseEntity<Unit> {
        finkService.deleteFink(
            userService.getUserIdFromSecurityContext(),
            finkId
        )
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/finks")
    fun getUserFinks(): ResponseEntity<List<UserlessFinkResponse>> {
        return ResponseEntity.ok(
            finkService.getFinksByUserId(
                userService.getUserIdFromSecurityContext()
            )
        )
    }

    @GetMapping("/finks/{finkId}")
    fun getFinkById(
        @PathVariable finkId: Long
    ): ResponseEntity<UserlessFinkResponse> {
        return ResponseEntity.ok(
            finkService.getFinkById(
                userService.getUserIdFromSecurityContext(),
                finkId
            )
        )
    }

}