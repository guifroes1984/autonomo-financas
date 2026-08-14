package com.autonomofinancas.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.response.RelatorioCategoriaResponse;
import com.autonomofinancas.dto.response.RelatorioComparativoResponse;
import com.autonomofinancas.dto.response.RelatorioPlataformaResponse;
import com.autonomofinancas.dto.response.RelatorioResumoResponse;
import com.autonomofinancas.service.RelatorioExportacaoService;
import com.autonomofinancas.service.RelatorioService;
import com.autonomofinancas.service.RelatorioTemplateService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class RelatorioExportacaoServiceImpl implements RelatorioExportacaoService {

    private final RelatorioService relatorioService;
    private final RelatorioTemplateService relatorioTemplateService;

    public RelatorioExportacaoServiceImpl(
            RelatorioService relatorioService,
            RelatorioTemplateService relatorioTemplateService) {

        this.relatorioService = relatorioService;
        this.relatorioTemplateService = relatorioTemplateService;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] gerarPdf(LocalDate inicio, LocalDate fim) {

        RelatorioResumoResponse resumo = relatorioService.obteresumo(inicio, fim);

        List<RelatorioPlataformaResponse> plataformas = relatorioService.obterReceitasPorPlataforma(inicio, fim);

        List<RelatorioCategoriaResponse> categorias = relatorioService.obterDespesasPorCategoria(inicio, fim);

        RelatorioComparativoResponse comparativo = relatorioService.obterComparativo(inicio, fim);

        String html = relatorioTemplateService.criarHtmlResumo(
            resumo, plataformas, categorias, comparativo);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Não foi possível gerar o relatório em PDF.",
                    exception);

        }
    }

}
