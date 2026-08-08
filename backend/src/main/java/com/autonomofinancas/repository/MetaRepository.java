package com.autonomofinancas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autonomofinancas.entity.Meta;

public interface MetaRepository extends JpaRepository<Meta, Long> {

    Optional<Meta> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioId(Long usuarioId);
    
}
