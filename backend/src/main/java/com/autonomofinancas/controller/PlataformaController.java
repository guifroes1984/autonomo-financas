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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {

    private final PlataformaService plataformaService;

    public PlataformaController(PlataformaService plataformaService) {
        this.plataformaService = plataformaService;
    }

    @PostMapping
    public ResponseEntity<PlataformaResponse> criar(@Valid @RequestBody PlataformaRequest request) {
        PlataformaResponse response = plataformaService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlataformaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(plataformaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PlataformaResponse>> listar() {
        return ResponseEntity.ok(plataformaService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlataformaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlataformaRequest request) {

        return ResponseEntity.ok(plataformaService.atualizar(id, request));

    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        plataformaService.desativar(id);

        return ResponseEntity.noContent().build();
    }

}
