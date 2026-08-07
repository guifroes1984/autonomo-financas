package com.autonomofinancas.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.request.LancamentoFiltroRequest;
import com.autonomofinancas.dto.request.LancamentoRequest;
import com.autonomofinancas.dto.response.LancamentoResponse;
import com.autonomofinancas.service.LancamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
@Tag(name = "Lançamentos", description = "Gerenciamento das receitas e despesas do usuário autenticado.")
public class LancamentoController {

        private final LancamentoService lancamentoService;

        public LancamentoController(LancamentoService lancamentoService) {
                this.lancamentoService = lancamentoService;
        }

        @Operation(summary = "Cadastrar lançamento", description = "Cadastra uma nova receita ou despesa.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Lançamento cadastrado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Categoria ou plataforma não encontrada.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Regra de negócio violada.", content = @Content)
        })
        @PostMapping
        public ResponseEntity<LancamentoResponse> criar(@Valid @RequestBody LancamentoRequest request) {

                LancamentoResponse response = lancamentoService.criar(request);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @Operation(summary = "Cadastrar lançamento", description = "Cadastra uma nova receita ou despesa.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Lançamento cadastrado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Categoria ou plataforma não encontrada.", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Regra de negócio violada.", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<LancamentoResponse> buscarPorId(

                        @Parameter(description = "Identificador do lançamento.", example = "1") @PathVariable Long id) {

                return ResponseEntity.ok(
                                lancamentoService.buscarPorId(id));

        }

        @Operation(summary = "Listar lançamentos", description = """
                        Lista os lançamentos do usuário autenticado.

                        Permite utilizar filtros por:
                        • Tipo
                        • Categoria
                        • Plataforma
                        • Período
                        • Descrição

                        Também suporta paginação e ordenação.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lançamentos listados com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetros de filtro inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content)
        })
        @GetMapping
        public ResponseEntity<Page<LancamentoResponse>> listar(

                        @ParameterObject LancamentoFiltroRequest filtro,

                        @ParameterObject @PageableDefault(size = 20, sort = "dataLancamento", direction = Sort.Direction.DESC) Pageable pageable) {

                return ResponseEntity.ok(
                                lancamentoService.listar(filtro, pageable));
        }

        @Operation(summary = "Atualizar lançamento", description = "Atualiza um lançamento pertencente ao usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lançamento atualizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Lançamento não encontrado.", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<LancamentoResponse> atualizar(
                        @PathVariable Long id,
                        @Valid @RequestBody LancamentoRequest request) {

                return ResponseEntity.ok(lancamentoService.atualizar(id, request));

        }

        @Operation(summary = "Excluir lançamento", description = "Remove um lançamento pertencente ao usuário autenticado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Lançamento excluído com sucesso."),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Lançamento não encontrado.", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> excluir(@PathVariable Long id) {

                lancamentoService.excluir(id);

                return ResponseEntity.noContent().build();
        }

}