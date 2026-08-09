package com.autonomofinancas.service;

import com.autonomofinancas.dto.request.AlterarSenhaRequest;
import com.autonomofinancas.dto.request.AtualizarUsuarioRequest;
import com.autonomofinancas.dto.request.CriarUsuarioRequest;
import com.autonomofinancas.dto.response.UsuarioPerfilResponse;
import com.autonomofinancas.dto.response.UsuarioResponse;

public interface UsuarioService {

    UsuarioResponse criar(CriarUsuarioRequest request);

    UsuarioPerfilResponse buscarPerfil();

    UsuarioPerfilResponse atualizarPerfil(AtualizarUsuarioRequest request);

    void alterarSenha(AlterarSenhaRequest request);
    
}
