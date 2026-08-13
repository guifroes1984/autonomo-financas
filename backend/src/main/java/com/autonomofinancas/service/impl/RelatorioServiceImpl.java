package com.autonomofinancas.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.response.IndicadorDiaResponse;
import com.autonomofinancas.dto.response.RelatorioCategoriaResponse;
import com.autonomofinancas.dto.response.RelatorioComparativoResponse;
import com.autonomofinancas.dto.response.RelatorioPlataformaResponse;
import com.autonomofinancas.dto.response.RelatorioResumoResponse;
import com.autonomofinancas.entity.enums.TipoLancamento;
import com.autonomofinancas.exception.RegraDeNegocioException;
import com.autonomofinancas.projection.DespesasPorCategoriaProjection;
import com.autonomofinancas.projection.EvolucaoDiariaProjection;
import com.autonomofinancas.projection.PlataformaReceitaProjection;
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

        @Override
        @Transactional(readOnly = true)
        public List<RelatorioPlataformaResponse> obterReceitasPorPlataforma(LocalDate inicio, LocalDate fim) {

                validarPeriodo(inicio, fim);

                Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

                List<PlataformaReceitaProjection> receitasPorPlataforma = lancamentoRepository
                                .buscarReceitasPorPlataforma(
                                                usuarioId,
                                                inicio,
                                                fim);

                BigDecimal totalReceitas = receitasPorPlataforma
                                .stream()
                                .map(PlataformaReceitaProjection::getTotalReceitas)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return receitasPorPlataforma
                                .stream()
                                .map(item -> {

                                        BigDecimal percentual = BigDecimal.ZERO.setScale(2);

                                        if (totalReceitas.compareTo(BigDecimal.ZERO) > 0) {

                                                percentual = item.getTotalReceitas()
                                                                .multiply(BigDecimal.valueOf(100))
                                                                .divide(
                                                                                totalReceitas,
                                                                                2,
                                                                                RoundingMode.HALF_UP);
                                        }

                                        return new RelatorioPlataformaResponse(
                                                        item.getPlataforma(),
                                                        item.getTotalReceitas().setScale(2, RoundingMode.HALF_UP),
                                                        percentual);
                                })
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<RelatorioCategoriaResponse> obterDespesasPorCategoria(LocalDate inicio, LocalDate fim) {

                validarPeriodo(inicio, fim);

                Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

                List<DespesasPorCategoriaProjection> despesasPorCategoria = lancamentoRepository
                                .buscarDespesasPorCategoria(
                                                usuarioId,
                                                inicio,
                                                fim);

                BigDecimal totalDespesas = despesasPorCategoria
                                .stream()
                                .map(DespesasPorCategoriaProjection::getTotalDespesas)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return despesasPorCategoria
                                .stream()
                                .map(item -> {

                                        BigDecimal percentual = BigDecimal.ZERO.setScale(2);

                                        if (totalDespesas.compareTo(BigDecimal.ZERO) > 0) {

                                                percentual = item.getTotalDespesas()
                                                                .multiply(BigDecimal.valueOf(100))
                                                                .divide(
                                                                                totalDespesas,
                                                                                2,
                                                                                RoundingMode.HALF_UP);
                                        }

                                        return new RelatorioCategoriaResponse(
                                                        item.getCategoria(),
                                                        item.getTotalDespesas()
                                                                        .setScale(2, RoundingMode.HALF_UP),
                                                        percentual);
                                })
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public RelatorioComparativoResponse obterComparativo(LocalDate inicio, LocalDate fim) {

                validarPeriodo(inicio, fim);

                Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

                long quantidadeDias = ChronoUnit.DAYS.between(inicio, fim) + 1;

                LocalDate fimPeriodoAnterior = inicio.minusDays(1);

                LocalDate inicioPeriodoAnterior = fimPeriodoAnterior.minusDays(quantidadeDias - 1);

                BigDecimal receitasPeriodoAnterior = lancamentoRepository.somarValorPorTipoEPeriodo(
                                usuarioId,
                                TipoLancamento.RECEITA,
                                inicioPeriodoAnterior,
                                fimPeriodoAnterior)
                                .setScale(2, RoundingMode.HALF_UP);

                BigDecimal despesasPeriodoAnterior = lancamentoRepository.somarValorPorTipoEPeriodo(
                                usuarioId,
                                TipoLancamento.DESPESA,
                                inicioPeriodoAnterior,
                                fimPeriodoAnterior)
                                .setScale(2, RoundingMode.HALF_UP);

                BigDecimal saldoPeriodoAnterior = receitasPeriodoAnterior
                                .subtract(despesasPeriodoAnterior)
                                .setScale(2, RoundingMode.HALF_UP);

                BigDecimal receitasPeriodoAtual = lancamentoRepository.somarValorPorTipoEPeriodo(
                                usuarioId,
                                TipoLancamento.RECEITA,
                                inicio,
                                fim)
                                .setScale(2, RoundingMode.HALF_UP);

                BigDecimal despesasPeriodoAtual = lancamentoRepository.somarValorPorTipoEPeriodo(
                                usuarioId,
                                TipoLancamento.DESPESA,
                                inicio,
                                fim)
                                .setScale(2, RoundingMode.HALF_UP);

                BigDecimal saldoPeriodoAtual = receitasPeriodoAtual
                                .subtract(despesasPeriodoAtual)
                                .setScale(2, RoundingMode.HALF_UP);

                BigDecimal variacaoReceitas = calcularVariacaoPercentual(
                                receitasPeriodoAnterior,
                                receitasPeriodoAtual);

                BigDecimal variacaoDespesas = calcularVariacaoPercentual(
                                despesasPeriodoAnterior,
                                despesasPeriodoAtual);

                BigDecimal variacaoSaldo = calcularVariacaoPercentual(
                                saldoPeriodoAnterior,
                                saldoPeriodoAtual);

                return new RelatorioComparativoResponse(
                                inicioPeriodoAnterior,
                                fimPeriodoAnterior,
                                inicio,
                                fim,
                                receitasPeriodoAnterior,
                                receitasPeriodoAtual,
                                variacaoReceitas,
                                despesasPeriodoAnterior,
                                despesasPeriodoAtual,
                                variacaoDespesas,
                                saldoPeriodoAnterior,
                                saldoPeriodoAtual,
                                variacaoSaldo);
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

        private BigDecimal calcularVariacaoPercentual(
                        BigDecimal valorAnterior,
                        BigDecimal valorAtual) {

                if (valorAnterior.compareTo(BigDecimal.ZERO) == 0) {
                        return BigDecimal.ZERO.setScale(2);
                }

                return valorAtual
                                .subtract(valorAnterior)
                                .multiply(BigDecimal.valueOf(100))
                                .divide(
                                                valorAnterior,
                                                2,
                                                RoundingMode.HALF_UP);
        }

}
