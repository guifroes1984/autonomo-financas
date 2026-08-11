package com.autonomofinancas.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autonomofinancas.entity.TokenRecuperacaoSenha;

public interface TokenRecuperacaoSenhaRepository extends JpaRepository<TokenRecuperacaoSenha, Long> {

    Optional<TokenRecuperacaoSenha> findByToken(String token);

    List<TokenRecuperacaoSenha> findByUsuarioIdAndUtilizadoFalse(Long usuarioId);

    List<TokenRecuperacaoSenha> findByExpiracaoBefore(OffsetDateTime dataHora);
    
}
