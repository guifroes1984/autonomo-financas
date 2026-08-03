package com.autonomofinancas.service;

import java.time.LocalDate;

import com.autonomofinancas.dto.response.DashboardResumoResponse;

public interface DashboardService {

    DashboardResumoResponse obterResumoHoje(LocalDate data);
    
}
