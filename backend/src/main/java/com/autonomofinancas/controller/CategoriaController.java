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

import com.autonomofinancas.dto.request.CategoriaRequest;
import com.autonomofinancas.dto.response.CategoriaResponse;
import com.autonomofinancas.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Gerenciamento das categorias utilizadas nos lançamentos financeiros.")
public class CategoriaController {

        private final CategoriaService categoriaService;

        public CategoriaController(CategoriaService categoriaService) {
                this.categoriaService = categoriaService;
        }

        @Operation(summary = "Cadastrar categoria", description = "Cadastra uma nova categoria de receita ou despesa para o usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Já existe uma categoria com o mesmo nome e tipo.", content = @Content)
        })
        @PostMapping
        public ResponseEntity<CategoriaResponse> criar(
                        @Valid @RequestBody CategoriaRequest request) {

                CategoriaResponse response = categoriaService.criar(request);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @Operation(summary = "Buscar categoria por ID", description = "Busca uma categoria pertencente ao usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso."),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<CategoriaResponse> buscarPorId(
                        @Parameter(description = "Identificador da categoria.", example = "1") @PathVariable Long id) {

                return ResponseEntity.ok(
                                categoriaService.buscarPorId(id));
        }

        @Operation(summary = "Listar categorias", description = "Lista todas as categorias do usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso."),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content)
        })
        @GetMapping
        public ResponseEntity<List<CategoriaResponse>> listar() {

                return ResponseEntity.ok(
                                categoriaService.listar());
        }

        @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria pertencente ao usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Já existe uma categoria com o mesmo nome e tipo.", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<CategoriaResponse> atualizar(
                        @Parameter(description = "Identificador da categoria.", example = "1") @PathVariable Long id,

                        @Valid @RequestBody CategoriaRequest request) {

                return ResponseEntity.ok(
                                categoriaService.atualizar(id, request));
        }

        @Operation(summary = "Desativar categoria", description = "Desativa uma categoria pertencente ao usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Categoria desativada com sucesso."),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "A categoria já está desativada.", content = @Content)
        })
        @PatchMapping("/{id}/desativar")
        public ResponseEntity<Void> desativar(
                        @Parameter(description = "Identificador da categoria.", example = "1") @PathVariable Long id) {

                categoriaService.desativar(id);

                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Ativar categoria", description = "Ativa uma categoria pertencente ao usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Categoria ativada com sucesso."),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "A categoria já está ativa.", content = @Content)
        })
        @PatchMapping("/{id}/ativar")
        public ResponseEntity<Void> ativar(
                        @Parameter(description = "Identificador da categoria.", example = "1") @PathVariable Long id) {

                categoriaService.ativar(id);

                return ResponseEntity.noContent().build();

        }

}
