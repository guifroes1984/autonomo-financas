package com.autonomofinancas.service;

import java.time.LocalDate;
import java.util.List;

import com.autonomofinancas.dto.response.RelatorioCategoriaResponse;
import com.autonomofinancas.dto.response.RelatorioComparativoResponse;
import com.autonomofinancas.dto.response.RelatorioPlataformaResponse;
import com.autonomofinancas.dto.response.RelatorioResumoResponse;

public interface RelatorioService {

    RelatorioResumoResponse obteresumo(LocalDate inicio, LocalDate fim);

    List<RelatorioPlataformaResponse> obterReceitasPorPlataforma(LocalDate inicio, LocalDate fim);

    List <RelatorioCategoriaResponse> obterDespesasPorCategoria(LocalDate inicio, LocalDate fim);

    RelatorioComparativoResponse obterComparativo(LocalDate inicio, LocalDate fim);
    
}
