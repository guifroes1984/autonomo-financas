package com.autonomofinancas.service;

import com.autonomofinancas.dto.request.EsqueciSenhaRequest;
import com.autonomofinancas.dto.request.RedefinirSenhaRequest;

public interface RecuperacaoSenhaService {

    void solicitarRecuperacaoSenha(EsqueciSenhaRequest request);

    void redefinirSenha(RedefinirSenhaRequest request);
    
}
