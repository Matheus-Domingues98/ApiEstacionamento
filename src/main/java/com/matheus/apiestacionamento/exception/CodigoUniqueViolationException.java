package com.matheus.apiestacionamento.exception;

public class CodigoUniqueViolationException extends RuntimeException {
    public CodigoUniqueViolationException(String messege) {
        super(messege);
    }
}
