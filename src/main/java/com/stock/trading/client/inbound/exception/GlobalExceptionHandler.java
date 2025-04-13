package com.stock.trading.client.inbound.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;
import java.util.logging.Level;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = Logger.getLogger(getClass().getName());

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Object> handleRateLimitExceeded(RateLimitExceededException ex) {
        log.warning(() -> "Rate limit exceeded: %s".formatted(ex.getMessage()));
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .body(new JsonError(HttpStatus.TOO_MANY_REQUESTS.name(), ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warning(() -> "Resource not found: %s".formatted(ex.getMessage()));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(new JsonError(HttpStatus.NOT_FOUND.name(), ex.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warning(() -> "Validation exception occurred: %s".formatted(ex.getMessage()));
        var error = new JsonError("VALIDATION_ERROR", "Validation failed");

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                error.details().put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.log(Level.SEVERE, "An unexpected error occurred", ex);
        var errorResponse = new JsonError(HttpStatus.INTERNAL_SERVER_ERROR.name(), "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}