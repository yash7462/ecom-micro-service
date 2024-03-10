package com.product.controller;

import com.product.config.ApiResponse;
import com.product.config.anotation.AuthValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalExceptions(Exception ex) {
        // Log the exception
        ex.printStackTrace();

        // Format the error message
        String errorMessage = "An unexpected error occurred: " + ex.getMessage();

        // Return the error response to the client
        return new ResponseEntity<>(new ApiResponse(false,errorMessage,null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthValidationException.class)
    public ResponseEntity<ApiResponse> handleAuthValidationException(AuthValidationException ex) {
        // Log the exceptions
        ex.printStackTrace();

        return new ResponseEntity<>(new ApiResponse(false,ex.getMessage(),null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
