package com.jvmaiaa.pokemon_api.infra.exception;

public class SortInvalidException extends RuntimeException {

    public SortInvalidException(String message) {
        super(message);
    }
}
