package com.autonomofinancas.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.response.DashboardIndicadoresResponse;
import com.autonomofinancas.dto.response.DashboardResumoResponse;
import com.autonomofinancas.dto.response.EvolucaoDiariaResponse;
import com.autonomofinancas.dto.response.IndicadorDiaResponse;
import com.autonomofinancas.entity.enums.TipoLancamento;
import com.autonomofinancas.exception.RegraDeNegocioException;
import com.autonomofinancas.projection.DespesasPorCategoriaProjection;
import com.autonomofinancas.projection.EvolucaoDiariaProjection;
import com.autonomofinancas.projection.PlataformaReceitaProjection;
import com.autonomofinancas.repository.LancamentoRepository;
import com.autonomofinancas.service.DashboardService;
import com.autonomofinancas.service.UsuarioAutenticadoService;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final LancamentoRepository lancamentoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public DashboardServiceImpl(
            LancamentoRepository lancamentoRepository,
            UsuarioAutenticadoService usuarioAutenticadoService) {

        this.lancamentoRepository = lancamentoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardResumoResponse obterResumo(LocalDate inicio, LocalDate fim) {

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        LocalDate dataInicio;
        LocalDate dataFim;

        if (inicio == null && fim == null) {
            dataInicio = LocalDate.now();
            dataFim = LocalDate.now();
        } else {
            validarPeriodo(inicio, fim);

            dataInicio = inicio;
            dataFim = fim;
        }

        BigDecimal totalReceitas = lancamentoRepository.somarValorPorTipoEPeriodo(
                usuarioId,
                TipoLancamento.RECEITA,
                dataInicio,
                dataFim);

        BigDecimal totalDespesas = lancamentoRepository.somarValorPorTipoEPeriodo(
                usuarioId,
                TipoLancamento.DESPESA,
                dataInicio,
                dataFim);

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        return new DashboardResumoResponse(
                totalReceitas,
                totalDespesas,
                saldo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlataformaReceitaProjection> obterReceitasPorPlataforma(LocalDate inicio, LocalDate fim) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        LocalDate dataInicio;
        LocalDate dataFim;

        if (inicio == null && fim == null) {
            dataInicio = LocalDate.now();
            dataFim = LocalDate.now();
        } else {
            validarPeriodo(inicio, fim);

            dataInicio = inicio;
            dataFim = fim;
        }

        return lancamentoRepository.buscarReceitasPorPlataforma(usuarioId, dataInicio, dataFim);

    }

    @Override
    @Transactional(readOnly = true)
    public List<DespesasPorCategoriaProjection> obterDespesasPorCategoria(LocalDate inicio, LocalDate fim) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        LocalDate dataInicio;
        LocalDate dataFim;

        if (inicio == null && fim == null) {
            dataInicio = LocalDate.now();
            dataFim = LocalDate.now();
        } else {

            validarPeriodo(inicio, fim);

            dataInicio = inicio;
            dataFim = fim;

        }

        return lancamentoRepository.buscarDespesasPorCategoria(usuarioId, dataInicio, dataFim);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoDiariaResponse> obterEvolucaoDiaria(
            LocalDate inicio,
            LocalDate fim) {

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        LocalDate dataInicio;
        LocalDate dataFim;

        if (inicio == null && fim == null) {

            dataInicio = LocalDate.now();
            dataFim = LocalDate.now();

        } else {

            validarPeriodo(inicio, fim);

            dataInicio = inicio;
            dataFim = fim;
        }

        return lancamentoRepository
                .buscarEvolucaoDiaria(
                        usuarioId,
                        dataInicio,
                        dataFim)
                .stream()
                .map(item -> {

                    BigDecimal saldo = item.getTotalReceitas()
                            .subtract(item.getTotalDespesas());

                    return new EvolucaoDiariaResponse(
                            item.getData(),
                            item.getTotalReceitas(),
                            item.getTotalDespesas(),
                            saldo);

                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardIndicadoresResponse obterIndicadores(
            LocalDate inicio, 
            LocalDate fim) {
        
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        LocalDate dataInicio;
        LocalDate dataFim;

        if (inicio == null && fim == null) {
            dataInicio = LocalDate.now();
            dataFim = LocalDate.now();
        } else {
            validarPeriodo(inicio, fim);

            dataInicio = inicio;
            dataFim = fim;
        }

        List<EvolucaoDiariaProjection> evolucao = 
            lancamentoRepository.buscarEvolucaoDiaria(
                usuarioId, 
                dataInicio, 
                dataFim
            );

        if (evolucao.isEmpty()) {
            return new DashboardIndicadoresResponse(
                null, 
                null, 
                BigDecimal.ZERO, 
                0L, 
                null, 
                null
            );
        }

        IndicadorDiaResponse melhorDia = null;
        IndicadorDiaResponse piorDia = null;

        BigDecimal somaSaldos = BigDecimal.ZERO;

        long diasTrabalhados = 0;

        for (EvolucaoDiariaProjection item : evolucao) {

            BigDecimal saldo = 
                    item.getTotalReceitas()
                        .subtract(item.getTotalDespesas());

            somaSaldos = somaSaldos.add(saldo);

            diasTrabalhados ++;

            if (melhorDia == null) {
                melhorDia = new IndicadorDiaResponse(
                    item.getData(), 
                    saldo
                );

                piorDia = new IndicadorDiaResponse(
                    item.getData(), 
                    saldo
                );

                continue;
            }

            if (saldo.compareTo(melhorDia.getSaldo()) > 0) {
                melhorDia = new IndicadorDiaResponse(
                    item.getData(), 
                    saldo
                );
            }

            if (saldo.compareTo(piorDia.getSaldo()) < 0) {
                piorDia = new IndicadorDiaResponse(
                    item.getData(), 
                    saldo
                );
            }

        }

        BigDecimal mediaDiaria = 
                somaSaldos.divide(
                    BigDecimal.valueOf(diasTrabalhados), 
                    2, 
                    RoundingMode.HALF_UP
                );

        List<PlataformaReceitaProjection> receitasPorPlataforma = 
                lancamentoRepository.buscarReceitasPorPlataforma(
                    usuarioId, 
                    dataInicio, 
                    dataFim
                );

        List<DespesasPorCategoriaProjection> despesasPorCategoria =
            lancamentoRepository.buscarDespesasPorCategoria(
                    usuarioId,
                    dataInicio,
                    dataFim
                );

        String melhorPlataforma = 
                receitasPorPlataforma.isEmpty()
                        ? null 
                        : receitasPorPlataforma.get(0).getPlataforma();

        String maiorCategoriaDespesa = 
                despesasPorCategoria.isEmpty()
                        ? null 
                        : despesasPorCategoria.get(0).getCategoria();                

        return new DashboardIndicadoresResponse(
            melhorDia, 
            piorDia, 
            mediaDiaria, 
            diasTrabalhados, 
            melhorPlataforma, 
            maiorCategoriaDespesa
        );
        
    }

    private void validarPeriodo(
            LocalDate inicio,
            LocalDate fim) {

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
