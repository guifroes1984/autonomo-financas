package com.autonomofinancas.dto.response;

public record RefreshTokenResponse(

        String accessToken,
        String tokenType,
        long expiresIn) {
}
