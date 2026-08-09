package com.autonomofinancas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.response.DashboardIndicadoresResponse;
import com.autonomofinancas.dto.response.DashboardMetaResponse;
import com.autonomofinancas.dto.response.DashboardResumoResponse;
import com.autonomofinancas.dto.response.EvolucaoDiariaResponse;
import com.autonomofinancas.projection.DespesasPorCategoriaProjection;
import com.autonomofinancas.projection.PlataformaReceitaProjection;
import com.autonomofinancas.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Consultas financeiras, indicadores e dados analíticos do usuário autenticado.")
public class DashboardController {

        private final DashboardService dashboardService;

        public DashboardController(DashboardService dashboardService) {
                this.dashboardService = dashboardService;
        }

        @Operation(summary = "Obter resumo financeiro", description = """
                        Retorna o total de receitas, despesas e saldo do período.

                        Quando as datas não forem informadas, considera a data atual.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Resumo financeiro obtido com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetro de período inválido.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/resumo")
        public ResponseEntity<DashboardResumoResponse> obterResumo(
                        @Parameter(description = "Data inicial do período.", example = "2026-08-01") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @Parameter(description = "Data final do período.", example = "2026-08-31") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                return ResponseEntity.ok(
                                dashboardService.obterResumo(inicio, fim));
        }

        @Operation(summary = "Obter receitas por plataforma", description = """
                        Retorna as receitas agrupadas por plataforma e ordenadas
                        do maior para o menor valor.

                        Quando as datas não forem informadas, considera a data atual.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Receitas por plataforma obtidas com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetro de período inválido.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/receitas-plataformas")
        public ResponseEntity<List<PlataformaReceitaProjection>> obterReceitasPorPlataforma(

                        @Parameter(description = "Data inicial do período.", example = "2026-08-01") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @Parameter(description = "Data final do período.", example = "2026-08-31") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                return ResponseEntity.ok(
                                dashboardService.obterReceitasPorPlataforma(
                                                inicio,
                                                fim));
        }

        @Operation(summary = "Obter despesas por categoria", description = """
                        Retorna as despesas agrupadas por categoria e ordenadas
                        do maior para o menor valor.

                        Quando as datas não forem informadas, considera a data atual.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Despesas por categoria obtidas com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetro de período inválido.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/despesas-categorias")
        public ResponseEntity<List<DespesasPorCategoriaProjection>> obterDespesasPorCategoria(

                        @Parameter(description = "Data inicial do período.", example = "2026-08-01") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @Parameter(description = "Data final do período.", example = "2026-08-31") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                return ResponseEntity.ok(
                                dashboardService.obterDespesasPorCategoria(
                                                inicio,
                                                fim));
        }

        @Operation(summary = "Obter evolução financeira diária", description = """
                        Retorna receitas, despesas e saldo agrupados por dia
                        dentro do período informado.

                        Os dados podem ser utilizados na construção de gráficos.
                        Quando as datas não forem informadas, considera a data atual.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Evolução diária obtida com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetro de período inválido.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/evolucao-diaria")
        public ResponseEntity<List<EvolucaoDiariaResponse>> obterEvolucaoDiaria(

                        @Parameter(description = "Data inicial do período.", example = "2026-08-01") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @Parameter(description = "Data final do período.", example = "2026-08-31") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                return ResponseEntity.ok(
                                dashboardService.obterEvolucaoDiaria(
                                                inicio,
                                                fim));
        }

        @Operation(summary = "Obter indicadores financeiros", description = """
                        Retorna os principais indicadores financeiros do período:

                        • Melhor dia
                        • Pior dia
                        • Média diária
                        • Quantidade de dias trabalhados
                        • Plataforma com maior receita
                        • Categoria com maior despesa

                        Quando as datas não forem informadas, considera a data atual.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Indicadores financeiros obtidos com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetro de período inválido.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Período informado inválido.", content = @Content)
        })
        @GetMapping("/indicadores")
        public ResponseEntity<DashboardIndicadoresResponse> obterIndicadores(
                        @Parameter(description = "Data inicial do período.", example = "2026-08-01") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @Parameter(description = "Data final do período.", example = "2026-08-31") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                return ResponseEntity.ok(
                                dashboardService.obterIndicadores(inicio, fim));
        }

        @Operation(summary = "Obter progresso das metas", description = "Retorna o progresso das metas diária e mensal do usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Metas obtidas com sucesso."),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Meta não encontrada.", content = @Content)
        })
        @GetMapping("/metas")
        public ResponseEntity<DashboardMetaResponse> obterMetas() {

                return ResponseEntity.ok(
                                dashboardService.obterMetas());
        }
}