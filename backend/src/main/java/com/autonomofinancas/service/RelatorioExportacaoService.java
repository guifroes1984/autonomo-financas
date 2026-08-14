package com.autonomofinancas.service;

import java.time.LocalDate;

public interface RelatorioExportacaoService {

    byte[] gerarPdf(LocalDate inicio, LocalDate fim);
    
}
