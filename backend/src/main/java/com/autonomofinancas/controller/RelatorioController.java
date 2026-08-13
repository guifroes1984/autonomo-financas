package com.autonomofinancas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.response.RelatorioCategoriaResponse;
import com.autonomofinancas.dto.response.RelatorioComparativoResponse;
import com.autonomofinancas.dto.response.RelatorioPlataformaResponse;
import com.autonomofinancas.dto.response.RelatorioResumoResponse;

import com.autonomofinancas.service.RelatorioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/relatorios")
@Tag(name = "Relatórios", description = "Relatórios financeiros consolidados do usuário autenticado.")
public class RelatorioController {

        private final RelatorioService relatorioService;

        public RelatorioController(
                        RelatorioService relatorioService) {

                this.relatorioService = relatorioService;
        }

        @Operation(summary = "Obter resumo financeiro por período", description = """
                        Retorna um resumo consolidado do período informado,
                        incluindo receitas, despesas, saldo, média diária,
                        quantidade de dias trabalhados, melhor dia e pior dia.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Relatório obtido com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetros de data inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/resumo")
        public ResponseEntity<RelatorioResumoResponse> obterResumo(
                        @RequestParam LocalDate inicio,
                        @RequestParam LocalDate fim) {

                RelatorioResumoResponse response = relatorioService.obteresumo(inicio, fim);

                return ResponseEntity.ok(response);

        }

        @Operation(summary = "Obter receitas por plataforma", description = """
                        Retorna as receitas do usuário autenticado agrupadas
                        por plataforma no período informado, incluindo o valor
                        total e o percentual de participação de cada plataforma.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Receitas por plataforma obtidas com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetros de data inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/receitas-por-plataforma")
        public ResponseEntity<List<RelatorioPlataformaResponse>> obterReceitasPorPlataforma(

                        @Parameter(description = "Data inicial do relatório.", example = "2026-08-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @Parameter(description = "Data final do relatório.", example = "2026-08-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                List<RelatorioPlataformaResponse> response = relatorioService.obterReceitasPorPlataforma(
                                inicio,
                                fim);

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Obter despesas por categoria", description = """
                        Retorna as despesas do usuário autenticado agrupadas
                        por categoria no período informado, incluindo o valor
                        total e o percentual de participação de cada categoria.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Despesas por categoria obtidas com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetros de data inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/despesas-por-categoria")
        public ResponseEntity<List<RelatorioCategoriaResponse>> obterDespesasPorCategoria(

                        @Parameter(description = "Data inicial do relatório.", example = "2026-08-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @Parameter(description = "Data final do relatório.", example = "2026-08-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                List<RelatorioCategoriaResponse> response = relatorioService.obterDespesasPorCategoria(
                                inicio,
                                fim);

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Comparar períodos financeiros", description = """
                        Compara o período informado com o período imediatamente
                        anterior de mesma duração.

                        Retorna receitas, despesas e saldo dos dois períodos,
                        além da variação percentual de cada indicador.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Comparativo obtido com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetros de data inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/comparativo")
        public ResponseEntity<RelatorioComparativoResponse> obterComparativo(

                        @Parameter(description = "Data inicial do período atual.", example = "2026-08-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @Parameter(description = "Data final do período atual.", example = "2026-08-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                RelatorioComparativoResponse response = relatorioService.obterComparativo(
                                inicio,
                                fim);

                return ResponseEntity.ok(response);
        }

}
