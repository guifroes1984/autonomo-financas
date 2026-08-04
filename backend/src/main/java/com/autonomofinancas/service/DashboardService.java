package com.autonomofinancas.service;

import java.time.LocalDate;
import java.util.List;

import com.autonomofinancas.dto.response.DashboardResumoResponse;
import com.autonomofinancas.dto.response.EvolucaoDiariaResponse;
import com.autonomofinancas.projection.DespesasPorCategoriaProjection;
import com.autonomofinancas.projection.PlataformaReceitaProjection;

public interface DashboardService {

    DashboardResumoResponse obterResumo(LocalDate inicio, LocalDate fim);

    List<PlataformaReceitaProjection> obterReceitasPorPlataforma(LocalDate inicio, LocalDate fim);

    List<DespesasPorCategoriaProjection> obterDespesasPorCategoria(LocalDate inicio, LocalDate fim);

    List<EvolucaoDiariaResponse> obterEvolucaoDiaria(LocalDate inicio, LocalDate fim);
    
}
