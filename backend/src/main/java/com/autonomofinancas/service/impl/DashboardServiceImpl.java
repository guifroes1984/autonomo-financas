package com.autonomofinancas.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.response.DashboardResumoResponse;
import com.autonomofinancas.entity.enums.TipoLancamento;
import com.autonomofinancas.exception.RegraDeNegocioException;
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
