package com.autonomofinancas.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.response.DashboardResumoResponse;
import com.autonomofinancas.entity.enums.TipoLancamento;
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
    public DashboardResumoResponse obterResumoHoje() {

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        LocalDate hoje = LocalDate.now();

        BigDecimal totalReceitas = 
                lancamentoRepository.somarValorPorTipoEData(
                    usuarioId, 
                    TipoLancamento.RECEITA, 
                    hoje);

        BigDecimal totalDespesas = 
                lancamentoRepository.somarValorPorTipoEData(
                    usuarioId,
                    TipoLancamento.DESPESA, 
                    hoje);
                    
        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        return new DashboardResumoResponse(totalReceitas, totalDespesas, saldo);
        
    }
    
}
