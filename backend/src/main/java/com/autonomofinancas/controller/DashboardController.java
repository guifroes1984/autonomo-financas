package com.autonomofinancas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.response.DashboardResumoResponse;
import com.autonomofinancas.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoResponse> obterResumoHoje() {

        return ResponseEntity.ok(dashboardService.obterResumoHoje());

    }
    
}
