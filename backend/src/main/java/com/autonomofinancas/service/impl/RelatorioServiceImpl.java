package com.autonomofinancas.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.response.IndicadorDiaResponse;
import com.autonomofinancas.dto.response.RelatorioResumoResponse;
import com.autonomofinancas.entity.enums.TipoLancamento;
import com.autonomofinancas.exception.RegraDeNegocioException;
import com.autonomofinancas.projection.EvolucaoDiariaProjection;
import com.autonomofinancas.repository.LancamentoRepository;
import com.autonomofinancas.service.RelatorioService;
import com.autonomofinancas.service.UsuarioAutenticadoService;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    private final LancamentoRepository lancamentoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public RelatorioServiceImpl(
            LancamentoRepository lancamentoRepository,
            UsuarioAutenticadoService usuarioAutenticadoService) {

        this.lancamentoRepository = lancamentoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    @Override
    @Transactional(readOnly = true)
    public RelatorioResumoResponse obteresumo(LocalDate inicio, LocalDate fim) {

        validarPeriodo(inicio, fim);

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        BigDecimal totalReceitas = lancamentoRepository.somarValorPorTipoEPeriodo(
                usuarioId,
                TipoLancamento.RECEITA,
                inicio,
                fim)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalDespesas = lancamentoRepository.somarValorPorTipoEPeriodo(
                usuarioId,
                TipoLancamento.DESPESA,
                inicio,
                fim)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        List<EvolucaoDiariaProjection> evolucao = lancamentoRepository.buscarEvolucaoDiaria(
                usuarioId,
                inicio,
                fim);

        long diasTrabalhados = evolucao.size();

        BigDecimal somaSaldos = BigDecimal.ZERO;

        IndicadorDiaResponse melhorDia = null;
        IndicadorDiaResponse piorDia = null;

        for (EvolucaoDiariaProjection item : evolucao) {
            BigDecimal saldoDia = item.getTotalReceitas()
                    .subtract(item.getTotalDespesas());

            somaSaldos = somaSaldos.add(saldoDia);

            if (melhorDia == null) {

                melhorDia = new IndicadorDiaResponse(
                        item.getData(),
                        saldoDia);

                piorDia = new IndicadorDiaResponse(
                        item.getData(),
                        saldoDia);

                continue;
            }

            if (saldoDia.compareTo(melhorDia.getSaldo()) > 0) {
                melhorDia = new IndicadorDiaResponse(
                        item.getData(),
                        saldoDia);
            }

            if (saldoDia.compareTo(piorDia.getSaldo()) < 0) {
                piorDia = new IndicadorDiaResponse(
                        item.getData(),
                        saldoDia);
            }

        }

        BigDecimal mediaDiaria = diasTrabalhados == 0
                ? BigDecimal.ZERO
                : somaSaldos.divide(
                        BigDecimal.valueOf(diasTrabalhados),
                        2,
                        RoundingMode.HALF_UP);

        return new RelatorioResumoResponse(
                inicio,
                fim,
                totalReceitas,
                totalDespesas,
                saldo,
                mediaDiaria,
                diasTrabalhados,
                melhorDia,
                piorDia);
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {

        if (inicio == null || fim == null) {
            throw new RegraDeNegocioException(
                    "As datas de início e fim devem ser informadas juntas.");
        }

        if (inicio.isAfter(fim)) {
            throw new RegraDeNegocioException(
                    "A data inicial não pode ser posterior à data final.");
        }

    }

}
