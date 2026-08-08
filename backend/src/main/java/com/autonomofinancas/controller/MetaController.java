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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/meta")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @PostMapping
    public ResponseEntity<MetaResponse> criar(@Valid @RequestBody MetaRequest request) {

        MetaResponse response = metaService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<MetaResponse> buscar() {

        return ResponseEntity.ok(metaService.buscar());

    }

    @PutMapping
    public ResponseEntity<MetaResponse> atualizar(@Valid @RequestBody MetaRequest request) {

        return ResponseEntity.ok(metaService.atualizar(request));

    }

}
