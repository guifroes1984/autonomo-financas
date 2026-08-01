package com.autonomofinancas.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.autonomofinancas.service.UsuarioAutenticadoService;

@Service
public class UsuarioAutenticadoServiceImpl
        implements UsuarioAutenticadoService {

    private static final String CLAIM_USUARIO_ID = "userId";

    @Override
    public Long obterUsuarioId() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuthentication)) {
            throw new IllegalStateException(
                    "Não foi possível identificar o usuário autenticado.");
        }

        Number usuarioId = jwtAuthentication
                .getToken()
                .getClaim(CLAIM_USUARIO_ID);

        if (usuarioId == null) {
            throw new IllegalStateException(
                    "O token não contém o identificador do usuário.");
        }

        return usuarioId.longValue();
    }
}