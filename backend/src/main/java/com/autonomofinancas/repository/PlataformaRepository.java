package com.autonomofinancas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autonomofinancas.entity.Plataforma;

public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    List<Plataforma> findAllByUsuarioIdOrderByNomeAsc(Long usuarioId);

    Optional<Plataforma> findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean existsByUsuarioIdAndNomeIgnoreCase(Long usuarioId, String nome);
    
}
