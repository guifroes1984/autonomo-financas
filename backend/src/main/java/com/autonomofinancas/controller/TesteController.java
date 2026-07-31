package com.autonomofinancas.controller;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @GetMapping("/autenticado")
    public Map<String, Object> autenticado(Authentication authentication) {

        Jwt jwt = (Jwt) authentication.getPrincipal();

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("mensagem", "Usuário autenticado com sucesso!");
        response.put("usuario", authentication.getName());
        response.put("emitidoEm", jwt.getIssuedAt());
        response.put("expiraEm", jwt.getExpiresAt());
        response.put("agora", OffsetDateTime.now());

        return response;
    }
}
