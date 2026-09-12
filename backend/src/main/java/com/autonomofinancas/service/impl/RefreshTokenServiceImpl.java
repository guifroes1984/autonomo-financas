package com.autonomofinancas.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.config.JwtProperties;
import com.autonomofinancas.dto.response.RefreshTokenResponse;
import com.autonomofinancas.entity.RefreshToken;
import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.exception.RefreshTokenExpiradoException;
import com.autonomofinancas.exception.RefreshTokenInvalidoException;
import com.autonomofinancas.repository.RefreshTokenRepository;
import com.autonomofinancas.security.JwtService;
import com.autonomofinancas.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;
    private final JwtService jwtService;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository refreshTokenRepository,
            JwtProperties jwtProperties,
            JwtService jwtService) {

        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProperties = jwtProperties;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public String criar(Usuario usuario) {

        String token = gerarTokenAleatorio();
        String tokenHash = gerarHash(token);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsuario(usuario);
        refreshToken.setTokenHash(tokenHash);
        refreshToken.setDataExpiracao(
                OffsetDateTime.now()
                        .plusSeconds(jwtProperties.refreshExpiration()));
        refreshToken.setRevogado(false);

        refreshTokenRepository.save(refreshToken);

        return token;

    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken validar(String token) {

        String tokenHash = gerarHash(token);

        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenHashAndRevogadoFalse(tokenHash)
                .orElseThrow(() -> new RefreshTokenInvalidoException(
                        "Refresh token inválido."));

        if (refreshToken.getDataExpiracao()
                .isBefore(OffsetDateTime.now())) {

            throw new RefreshTokenExpiradoException(
                    "Refresh token expirado.");
        }

        return refreshToken;
    }

    @Override
    @Transactional
    public void revogar(String token) {

        String tokenHash = gerarHash(token);

        refreshTokenRepository
                .findByTokenHashAndRevogadoFalse(tokenHash)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevogado(true);
                    refreshTokenRepository.save(refreshToken);
                });
    }

    private String gerarTokenAleatorio() {

        byte[] bytes = new byte[64];

        SECURE_RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String gerarHash(String token) {

        try {

            MessageDigest digest = MessageDigest
                    .getInstance("SHA-256");

            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder()
                    .encodeToString(hash);

        } catch (NoSuchAlgorithmException exception) {

            throw new IllegalStateException(
                    "Erro ao gerar hash do refresh token.",
                    exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshTokenResponse renovar(String token) {

        RefreshToken refreshToken = validar(token);

        String accessToken = jwtService.gerarToken(
                refreshToken.getUsuario());

        return new RefreshTokenResponse(
                accessToken,
                "Bearer",
                jwtProperties.expiration());
    }

}
