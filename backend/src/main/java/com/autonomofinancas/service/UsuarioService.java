package com.autonomofinancas.service;

import com.autonomofinancas.dto.request.CriarUsuarioRequest;
import com.autonomofinancas.dto.response.UsuarioResponse;

public interface UsuarioService {

    UsuarioResponse criar(CriarUsuarioRequest request);
    
}
