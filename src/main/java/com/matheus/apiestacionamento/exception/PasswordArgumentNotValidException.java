package com.matheus.apiestacionamento.exception;

public class PasswordArgumentNotValidException extends RuntimeException {
    public PasswordArgumentNotValidException(String message) {
        super(message);
    }
}
