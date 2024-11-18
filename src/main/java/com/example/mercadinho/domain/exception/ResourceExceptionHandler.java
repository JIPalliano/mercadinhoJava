package com.example.mercadinho.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ResourceExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Map<String, String> handleMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ex.getLocalizedMessage(), ex.getMessage());
        return error;
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Map<String, String> handleResponseStatus(AuthorizationDeniedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ex.getLocalizedMessage(), ex.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Map<String, String> handleException(
            Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ex.getLocalizedMessage(), ex.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoSuchElementException.class})
    public Map<String, String> handleExceptionNotFound(
            NoSuchElementException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ex.getLocalizedMessage(), "Product not found.");
        return error;
    }
}
