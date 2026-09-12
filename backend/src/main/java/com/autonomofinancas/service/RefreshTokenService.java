package com.autonomofinancas.service;

import com.autonomofinancas.dto.response.RefreshTokenResponse;
import com.autonomofinancas.entity.RefreshToken;
import com.autonomofinancas.entity.Usuario;

public interface RefreshTokenService {
    
    String criar(Usuario usuario);

    RefreshToken validar(String token);

    void revogar(String token);

    RefreshTokenResponse renovar(String token);
}
