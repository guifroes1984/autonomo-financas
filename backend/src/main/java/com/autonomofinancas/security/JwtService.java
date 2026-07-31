package com.autonomofinancas.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.autonomofinancas.config.JwtProperties;

@Service
public class JwtService {

    private static final String ISSUER = "autonomo-financas";
    private static final String CLAIM_USER_ID = "userId";

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public JwtService(
            JwtEncoder jwtEncoder,
            JwtProperties jwtProperties) {

        this.jwtEncoder = jwtEncoder;
        this.jwtProperties = jwtProperties;
    }

    public String gerarToken(Authentication authentication) {

        UsuarioDetails usuario = (UsuarioDetails) authentication.getPrincipal();

        Instant agora = Instant.now();

        Instant expiracao = agora.plus(
                jwtProperties.expiration(),
                ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(agora)
                .expiresAt(expiracao)
                .subject(usuario.getUsername())
                .claim(CLAIM_USER_ID, usuario.getId())
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();
    }
}