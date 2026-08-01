package com.autonomofinancas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autonomofinancas.entity.Categoria;
import com.autonomofinancas.entity.enums.TipoCategoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findAllByUsuarioIdOrderByNomeAsc(Long usuarioId);

    List<Categoria> findAllByUsuarioIdAndTipoOrderByNomeAsc(
            Long usuarioId,
            TipoCategoria tipo);

    List<Categoria> findAllByUsuarioIdAndAtivaTrueOrderByNomeAsc(
            Long usuarioId);

    Optional<Categoria> findByIdAndUsuarioId(
            Long id,
            Long usuarioId);

    boolean existsByUsuarioIdAndNomeIgnoreCaseAndTipo(
            Long usuarioId,
            String nome,
            TipoCategoria tipo);

}
