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
import com.autonomofinancas.projection.DespesasPorCategoriaProjection;
import com.autonomofinancas.projection.PlataformaReceitaProjection;

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

    @Query("""
                SELECT
                    p.nome AS plataforma,
                    COALESCE(SUM(l.valor), 0) AS totalReceitas
                FROM Lancamento l
                JOIN l.plataforma p
                WHERE l.usuario.id = :usuarioId
                  AND l.tipo = com.autonomofinancas.entity.enums.TipoLancamento.RECEITA
                  AND l.dataLancamento BETWEEN :inicio AND :fim
                GROUP BY p.nome
                ORDER BY totalReceitas DESC
            """)
    List<PlataformaReceitaProjection> buscarReceitasPorPlataforma(
            @Param("usuarioId") Long usuarioId,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    @Query("""
                SELECT
                    c.nome AS categoria,
                    COALESCE(SUM(l.valor), 0) AS totalDespesas
                FROM Lancamento l
                JOIN l.categoria c
                WHERE l.usuario.id = :usuarioId
                  AND l.tipo = com.autonomofinancas.entity.enums.TipoLancamento.DESPESA
                  AND l.dataLancamento BETWEEN :inicio AND :fim
                GROUP BY c.nome
                ORDER BY totalDespesas DESC
            """)
    List<DespesasPorCategoriaProjection> buscarDespesasPorCategoria(
            @Param("usuarioId") Long usuarioId,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

}
