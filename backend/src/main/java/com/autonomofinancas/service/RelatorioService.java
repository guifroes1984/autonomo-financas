package com.autonomofinancas.service;

import java.time.LocalDate;

import com.autonomofinancas.dto.response.RelatorioResumoResponse;

public interface RelatorioService {

    RelatorioResumoResponse obteresumo(LocalDate inicio, LocalDate fim);
    
}
