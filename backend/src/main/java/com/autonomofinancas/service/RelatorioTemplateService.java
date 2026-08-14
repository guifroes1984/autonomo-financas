package com.autonomofinancas.service;

import java.util.List;

import com.autonomofinancas.dto.response.RelatorioCategoriaResponse;
import com.autonomofinancas.dto.response.RelatorioComparativoResponse;
import com.autonomofinancas.dto.response.RelatorioPlataformaResponse;
import com.autonomofinancas.dto.response.RelatorioResumoResponse;

public interface RelatorioTemplateService {

    String criarHtmlResumo(
        RelatorioResumoResponse resumo, 
        List<RelatorioPlataformaResponse> plataformas, 
        List<RelatorioCategoriaResponse> categorias, 
        RelatorioComparativoResponse comparativo);
    
}
