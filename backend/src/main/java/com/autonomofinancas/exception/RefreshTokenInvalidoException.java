package com.autonomofinancas.exception;

public class RefreshTokenInvalidoException extends RuntimeException {

    public RefreshTokenInvalidoException(String mensagem) {
        super(mensagem);
    }
}
