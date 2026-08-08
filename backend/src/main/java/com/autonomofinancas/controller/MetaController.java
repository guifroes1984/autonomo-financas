package com.autonomofinancas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.request.MetaRequest;
import com.autonomofinancas.dto.response.MetaResponse;
import com.autonomofinancas.security.MetaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/meta")
@Tag(name = "Meta", description = "Gerenciamento das metas financeiras do usuário.")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @Operation(summary = "Criar meta", description = "Cria a configuração de metas financeiras do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meta criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "O usuário já possui uma meta cadastrada.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MetaResponse> criar(@Valid @RequestBody MetaRequest request) {

        MetaResponse response = metaService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Operation(summary = "Buscar meta", description = "Obtém a meta financeira do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta encontrada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<MetaResponse> buscar() {

        return ResponseEntity.ok(metaService.buscar());

    }

    @Operation(summary = "Atualizar meta", description = "Atualiza a configuração de metas financeiras do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada.", content = @Content)
    })
    @PutMapping
    public ResponseEntity<MetaResponse> atualizar(@Valid @RequestBody MetaRequest request) {

        return ResponseEntity.ok(metaService.atualizar(request));

    }

}
