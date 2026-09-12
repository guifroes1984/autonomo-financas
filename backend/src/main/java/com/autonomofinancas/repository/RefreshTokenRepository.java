package com.autonomofinancas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autonomofinancas.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHashAndRevogadoFalse(String tokenHash);

}