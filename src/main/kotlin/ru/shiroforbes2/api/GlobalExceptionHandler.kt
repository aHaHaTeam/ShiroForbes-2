package ru.shiroforbes2.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.shiroforbes2.dto.response.MessageResponse
import ru.shiroforbes2.service.SheetLoaderService

@ControllerAdvice
internal class GlobalExceptionHandler {
  @ExceptionHandler(SheetLoaderService.RatingServiceException::class)
  fun handleRatingServiceError(): ResponseEntity<MessageResponse> =
    ResponseEntity
      .status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(MessageResponse("Error: Could not retrieve ratings."))
}
