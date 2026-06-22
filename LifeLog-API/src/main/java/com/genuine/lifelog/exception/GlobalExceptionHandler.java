package com.genuine.lifelog.exception;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.genuine.lifelog.dto.response.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse>
    validationException(ValidationException e) {

        ExceptionResponse response =
                new ExceptionResponse(
                        "failure",
                        e.getMessage(),
                        null,
                        e.getErrorList()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
    
     @ExceptionHandler(MethodArgumentTypeMismatchException.class)
     public ResponseEntity<ExceptionResponse>
     validationException(MethodArgumentTypeMismatchException e) {

         ExceptionResponse response =
                 new ExceptionResponse(
                         "failure",
                         e.getMessage(),
                         null,
                         List.of(e.getMessage())
                 );

         return ResponseEntity
                 .badRequest()
                 .body(response);
     }
    
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        List<String> errors =
                e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                error.getDefaultMessage())
                        .toList();

        ExceptionResponse response =
                new ExceptionResponse(
                        "failure",
                        "Validation failed",
                        null,
                        errors
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException e) {

        ExceptionResponse response =
                new ExceptionResponse(
                        "failure",
                        e.getMessage(),
                        null,
                        List.of(e.getMessage())
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(EmptyResultDataAccessException e) {

        ExceptionResponse response =
                new ExceptionResponse(
                        "failure",
                        "Record has already been deleted or not present.",
                        null,
                        List.of(e.getMessage())
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> generalException(Exception e) {

        ExceptionResponse response =
                new ExceptionResponse(
                        "failure",
                        "An error occurred.",
                        null,
                        List.of(e.getMessage())
                );

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(response);
    }
}