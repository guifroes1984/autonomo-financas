package com.autonomofinancas.exception;

public class RefreshTokenExpiradoException extends RuntimeException {

    public RefreshTokenExpiradoException(String mensagem) {
        super(mensagem);
    }
}
