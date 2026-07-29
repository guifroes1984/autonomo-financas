package com.autonomofinancas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autonomofinancas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCase(String email);
    
}
