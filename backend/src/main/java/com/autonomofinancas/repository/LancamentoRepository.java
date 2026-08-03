package com.autonomofinancas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autonomofinancas.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    Optional<Lancamento> findByIdAndUsuarioId(Long id, Long usuarioId);

    List<Lancamento> findAllByUsuarioIdOrderByDataLancamentoDescIdDesc(Long usuarioId);

    List<Lancamento> findAllByUsuarioIdAndDataLancamentoOrderByIdDesc(Long usuarioId, LocalDate dataLancamento);

}
