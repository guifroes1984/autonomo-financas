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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaRequest request) {

        CategoriaResponse response = categoriaService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(categoriaService.buscarPorId(id));

    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {

        return ResponseEntity.ok(categoriaService.listar());

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) {

        return ResponseEntity.ok(categoriaService.atualizar(id, request));

    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {

        categoriaService.desativar(id);

        return ResponseEntity.noContent().build();

    }

}
