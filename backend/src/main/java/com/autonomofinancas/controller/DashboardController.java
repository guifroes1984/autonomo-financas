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
import com.autonomofinancas.dto.response.DashboardResumoResponse;
import com.autonomofinancas.dto.response.EvolucaoDiariaResponse;
import com.autonomofinancas.projection.DespesasPorCategoriaProjection;
import com.autonomofinancas.projection.PlataformaReceitaProjection;
import com.autonomofinancas.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoResponse> obterResumo(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return ResponseEntity.ok(dashboardService.obterResumo(inicio, fim));

    }

    @GetMapping("/receitas-plataformas")
    public ResponseEntity<List<PlataformaReceitaProjection>> obterReceitasPorPlataforma(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return ResponseEntity.ok(dashboardService.obterReceitasPorPlataforma(inicio, fim));

    }

    @GetMapping("/despesas-categorias")
    public ResponseEntity<List<DespesasPorCategoriaProjection>> obterDespesasPorCategoria(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return ResponseEntity.ok(dashboardService.obterDespesasPorCategoria(inicio, fim));

    }

    @GetMapping("/evolucao-diaria")
    public ResponseEntity<List<EvolucaoDiariaResponse>> obterEvolucaoDiaria(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return ResponseEntity.ok(dashboardService.obterEvolucaoDiaria(inicio, fim));

    }

    @GetMapping("/indicadores")
    public ResponseEntity<DashboardIndicadoresResponse> obterIndicadores(
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate inicio,

        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate fim) {

            return ResponseEntity.ok(
                dashboardService.obterIndicadores(inicio, fim)
            );

    }

}
