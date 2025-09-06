package com.mohdiop.finkuprestapi.exception

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.nio.file.AccessDeniedException

@RestControllerAdvice
class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, String?>> {
        val errors = exception.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        return ResponseEntity(
            errors,
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(EntityExistsException::class)
    fun handleEntityExistsException(exception: EntityExistsException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(exception.message)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(exception: EntityNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(exception.message)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(exception: AccessDeniedException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(exception.message)
    }

}