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
    public DashboardResumoResponse obterResumoHoje(LocalDate data) {

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        LocalDate dataConsulta = data != null ? data : LocalDate.now();

        BigDecimal totalReceitas = 
                lancamentoRepository.somarValorPorTipoEData(
                    usuarioId, 
                    TipoLancamento.RECEITA, 
                    dataConsulta);

        BigDecimal totalDespesas = 
                lancamentoRepository.somarValorPorTipoEData(
                    usuarioId,
                    TipoLancamento.DESPESA, 
                    dataConsulta);
                    
        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        return new DashboardResumoResponse(totalReceitas, totalDespesas, saldo);
        
    }
    
}
