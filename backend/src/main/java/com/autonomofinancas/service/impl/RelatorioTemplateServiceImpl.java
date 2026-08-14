package com.autonomofinancas.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.autonomofinancas.dto.response.IndicadorDiaResponse;
import com.autonomofinancas.dto.response.RelatorioCategoriaResponse;
import com.autonomofinancas.dto.response.RelatorioComparativoResponse;
import com.autonomofinancas.dto.response.RelatorioPlataformaResponse;
import com.autonomofinancas.dto.response.RelatorioResumoResponse;
import com.autonomofinancas.service.RelatorioTemplateService;

@Service
public class RelatorioTemplateServiceImpl implements RelatorioTemplateService {

        private final ResourceLoader resourceLoader;

        public RelatorioTemplateServiceImpl(
                        ResourceLoader resourceLoader) {

                this.resourceLoader = resourceLoader;
        }

        @Override
        public String criarHtmlResumo(
                        RelatorioResumoResponse resumo,
                        List<RelatorioPlataformaResponse> plataformas,
                        List<RelatorioCategoriaResponse> categorias,
                        RelatorioComparativoResponse comparativo) {

                String html = carregarTemplate(
                                "classpath:templates/relatorio/relatorio-financeiro.html");

                return html
                                .replace(
                                                "{{DATA_INICIO}}",
                                                formatarData(resumo.getInicio()))
                                .replace(
                                                "{{DATA_FIM}}",
                                                formatarData(resumo.getFim()))
                                .replace(
                                                "{{TOTAL_RECEITAS}}",
                                                formatarMoeda(resumo.getTotalReceitas()))
                                .replace(
                                                "{{TOTAL_DESPESAS}}",
                                                formatarMoeda(resumo.getTotalDespesas()))
                                .replace(
                                                "{{SALDO}}",
                                                formatarMoeda(resumo.getSaldo()))
                                .replace(
                                                "{{MEDIA_DIARIA}}",
                                                formatarMoeda(resumo.getMediaDiaria()))
                                .replace(
                                                "{{DIAS_TRABALHADOS}}",
                                                resumo.getDiasTrabalhados().toString())
                                .replace(
                                                "{{MELHOR_DIA}}",
                                                formatarIndicadorDia(resumo.getMelhorDia()))
                                .replace(
                                                "{{PIOR_DIA}}",
                                                formatarIndicadorDia(resumo.getPiorDia()))
                                .replace(
                                                "{{LINHAS_PLATAFORMAS}}",
                                                criarLinhasPlataformas(plataformas))
                                .replace(
                                                "{{LINHAS_CATEGORIAS}}",
                                                criarLinhasCategorias(categorias))
                                .replace(
                                                "{{RECEITAS_ANTERIOR}}",
                                                formatarMoeda(comparativo.getReceitasPeriodoAnterior()))
                                .replace(
                                                "{{RECEITAS_ATUAL}}",
                                                formatarMoeda(comparativo.getReceitasPeriodoAtual()))
                                .replace(
                                                "{{VARIACAO_RECEITAS}}",
                                                formatarPercentual(comparativo.getVariacaoReceitas()))
                                .replace(
                                                "{{DESPESAS_ANTERIOR}}",
                                                formatarMoeda(comparativo.getDespesasPeriodoAnterior()))
                                .replace(
                                                "{{DESPESAS_ATUAL}}",
                                                formatarMoeda(comparativo.getDespesasPeriodoAtual()))
                                .replace(
                                                "{{VARIACAO_DESPESAS}}",
                                                formatarPercentual(comparativo.getVariacaoDespesas()))
                                .replace(
                                                "{{SALDO_ANTERIOR}}",
                                                formatarMoeda(comparativo.getSaldoPeriodoAnterior()))
                                .replace(
                                                "{{SALDO_ATUAL}}",
                                                formatarMoeda(comparativo.getSaldoPeriodoAtual()))
                                .replace(
                                                "{{VARIACAO_SALDO}}",
                                                formatarPercentual(comparativo.getVariacaoSaldo()));
        }

        private String carregarTemplate(String caminho) {

                try {

                        Resource resource = resourceLoader.getResource(caminho);

                        return new String(
                                        resource.getInputStream().readAllBytes(),
                                        StandardCharsets.UTF_8);

                } catch (IOException exception) {

                        throw new IllegalStateException(
                                        "Não foi possível carregar o template do relatório.",
                                        exception);
                }
        }

        private String formatarMoeda(BigDecimal valor) {

                NumberFormat formato = NumberFormat.getCurrencyInstance(
                                new Locale("pt", "BR"));

                return formato.format(valor);
        }

        private String formatarIndicadorDia(
                        IndicadorDiaResponse indicador) {

                if (indicador == null) {
                        return "-";
                }

                return formatarData(indicador.getData())
                                + " - "
                                + formatarMoeda(indicador.getSaldo());
        }

        private String criarLinhasPlataformas(
                        List<RelatorioPlataformaResponse> plataformas) {

                if (plataformas.isEmpty()) {
                        return """
                                        <tr>
                                            <td colspan="3">Nenhuma receita por plataforma no período.</td>
                                        </tr>
                                        """;
                }

                StringBuilder linhas = new StringBuilder();

                for (RelatorioPlataformaResponse plataforma : plataformas) {

                        linhas.append("""
                                        <tr>
                                            <td>%s</td>
                                            <td>%s</td>
                                            <td>%s</td>
                                        </tr>
                                        """.formatted(
                                        plataforma.getPlataforma(),
                                        formatarMoeda(plataforma.getTotal()),
                                        formatarPercentual(plataforma.getPercentual())));
                }

                return linhas.toString();
        }

        private String criarLinhasCategorias(
                        List<RelatorioCategoriaResponse> categorias) {

                if (categorias.isEmpty()) {
                        return """
                                        <tr>
                                            <td colspan="3">
                                                Nenhuma despesa por categoria no período.
                                            </td>
                                        </tr>
                                        """;
                }

                StringBuilder linhas = new StringBuilder();

                for (RelatorioCategoriaResponse categoria : categorias) {

                        linhas.append("""
                                        <tr>
                                            <td>%s</td>
                                            <td>%s</td>
                                            <td>%s</td>
                                        </tr>
                                        """.formatted(
                                        categoria.getCategoria(),
                                        formatarMoeda(categoria.getTotal()),
                                        formatarPercentual(categoria.getPercentual())));
                }

                return linhas.toString();
        }

        private String formatarPercentual(BigDecimal valor) {

                NumberFormat formato = NumberFormat.getNumberInstance(
                                new Locale("pt", "BR"));

                formato.setMinimumFractionDigits(2);
                formato.setMaximumFractionDigits(2);

                return formato.format(valor) + "%";
        }

        private String formatarData(LocalDate data) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                return data.format(formatter);
        }

}
