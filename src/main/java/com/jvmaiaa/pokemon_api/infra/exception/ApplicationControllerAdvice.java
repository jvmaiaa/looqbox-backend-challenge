package com.jvmaiaa.pokemon_api.infra.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(SortInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSortInvalidException(SortInvalidException ex) {
        return ex.getMessage();
    }
}
