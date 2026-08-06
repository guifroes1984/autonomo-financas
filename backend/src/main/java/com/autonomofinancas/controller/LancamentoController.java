package com.autonomofinancas.controller;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    private final LancamentoService lancamentoService;

    public LancamentoController(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    @PostMapping
    public ResponseEntity<LancamentoResponse> criar(@Valid @RequestBody LancamentoRequest request) {

        LancamentoResponse response = lancamentoService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(lancamentoService.buscarPorId(id));

    }

    @GetMapping
    public ResponseEntity<Page<LancamentoResponse>> listar(

            LancamentoFiltroRequest filtro, 
            @PageableDefault(
                size = 20,
                sort = "dataLancamento",
                direction = Sort.Direction.DESC
        )
        Pageable pageable) {

        return ResponseEntity.ok(lancamentoService.listar(filtro, pageable));

    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LancamentoRequest request) {

        return ResponseEntity.ok(lancamentoService.atualizar(id, request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        lancamentoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}