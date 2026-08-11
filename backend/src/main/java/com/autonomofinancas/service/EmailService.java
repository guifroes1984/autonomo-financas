package com.autonomofinancas.service;

import com.autonomofinancas.entity.Usuario;

public interface EmailService {

    void enviarRecuperacaoSenha(Usuario usuario, String token);
    
}
