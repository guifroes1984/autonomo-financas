package com.autonomofinancas.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autonomofinancas.entity.Lancamento;
import com.autonomofinancas.entity.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

        Optional<Lancamento> findByIdAndUsuarioId(Long id, Long usuarioId);

        List<Lancamento> findAllByUsuarioIdOrderByDataLancamentoDescIdDesc(Long usuarioId);

        List<Lancamento> findAllByUsuarioIdAndDataLancamentoOrderByIdDesc(Long usuarioId, LocalDate dataLancamento);

        @Query("""
                            SELECT COALESCE(SUM(l.valor), 0)
                            FROM Lancamento l
                            WHERE l.usuario.id = :usuarioId
                              AND l.tipo = :tipo
                              AND l.dataLancamento = :data
                        """)
        BigDecimal somarValorPorTipoEData(
                        @Param("usuarioId") Long usuarioId,
                        @Param("tipo") TipoLancamento tipo,
                        @Param("data") LocalDate data);

        @Query("""
                            SELECT COALESCE(SUM(l.valor), 0)
                            FROM Lancamento l
                            WHERE l.usuario.id = :usuarioId
                              AND l.tipo = :tipo
                              AND l.dataLancamento BETWEEN :inicio AND :fim
                        """)
        BigDecimal somarValorPorTipoEPeriodo(
                        @Param("usuarioId") Long usuarioId,
                        @Param("tipo") TipoLancamento tipo,
                        @Param("inicio") LocalDate inicio,
                        @Param("fim") LocalDate fim);

}
