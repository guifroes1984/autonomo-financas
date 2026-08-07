package com.autonomofinancas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.request.PlataformaRequest;
import com.autonomofinancas.dto.response.PlataformaResponse;
import com.autonomofinancas.service.PlataformaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/plataformas")
@Tag(name = "Plataformas", description = "Gerenciamento das plataformas utilizadas para registrar receitas.")
public class PlataformaController {

    private final PlataformaService plataformaService;

    public PlataformaController(PlataformaService plataformaService) {
        this.plataformaService = plataformaService;
    }

    @Operation(summary = "Cadastrar plataforma", description = "Cadastra uma nova plataforma para o usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plataforma cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Já existe uma plataforma com o mesmo nome.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PlataformaResponse> criar(@Valid @RequestBody PlataformaRequest request) {
        PlataformaResponse response = plataformaService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar plataforma por ID", description = "Busca uma plataforma pertencente ao usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plataforma encontrada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plataforma não encontrada.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlataformaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(plataformaService.buscarPorId(id));
    }

    @Operation(summary = "Listar plataformas", description = "Lista todas as plataformas do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plataformas listadas com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PlataformaResponse>> listar() {
        return ResponseEntity.ok(plataformaService.listar());
    }

    @Operation(summary = "Atualizar plataforma", description = "Atualiza os dados de uma plataforma pertencente ao usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plataforma atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plataforma não encontrada.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Já existe uma plataforma com o mesmo nome.", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlataformaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlataformaRequest request) {

        return ResponseEntity.ok(plataformaService.atualizar(id, request));

    }

    @Operation(summary = "Desativar plataforma", description = "Desativa uma plataforma pertencente ao usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Plataforma desativada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plataforma não encontrada.", content = @Content),
            @ApiResponse(responseCode = "409", description = "A plataforma já está desativada.", content = @Content)
    })
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        plataformaService.desativar(id);

        return ResponseEntity.noContent().build();
    }

}
