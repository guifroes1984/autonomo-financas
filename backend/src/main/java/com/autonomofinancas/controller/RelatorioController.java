package com.autonomofinancas.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.response.RelatorioResumoResponse;

import com.autonomofinancas.service.RelatorioService;

import io.swagger.v3.oas.annotations.Operation;
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

}
