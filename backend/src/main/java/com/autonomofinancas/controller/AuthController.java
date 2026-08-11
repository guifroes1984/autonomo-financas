package com.autonomofinancas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.request.EsqueciSenhaRequest;
import com.autonomofinancas.dto.request.LoginRequest;
import com.autonomofinancas.dto.request.RedefinirSenhaRequest;
import com.autonomofinancas.dto.response.LoginResponse;
import com.autonomofinancas.service.LoginService;
import com.autonomofinancas.service.RecuperacaoSenhaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Operação relacionadas à autenticação e geração de token JWT.")
public class AuthController {

    private final LoginService loginService;
    private final RecuperacaoSenhaService recuperacaoSenhaService;

    public AuthController(
            LoginService loginService,
            RecuperacaoSenhaService recuperacaoSenhaService) {
        this.loginService = loginService;
        this.recuperacaoSenhaService = recuperacaoSenhaService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos.", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = loginService.autenticar(request);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Solicitar recuperação de senha", description = "Gera um token para recuperação de senha do usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Solicitação processada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content)
    })
    @PostMapping("/esqueci-senha")
    public ResponseEntity<Void> solicitarRecuperacaoSenha(
            @Valid @RequestBody EsqueciSenhaRequest request) {

        recuperacaoSenhaService.solicitarRecuperacaoSenha(request);

        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Redefinir senha", description = "Redefine a senha utilizando um token de recuperação.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha redefinida com sucesso."),
            @ApiResponse(responseCode = "400", description = "Token inválido, expirado ou dados inválidos.", content = @Content)
    })
    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(
            @Valid @RequestBody RedefinirSenhaRequest request) {

        recuperacaoSenhaService.redefinirSenha(request);

        return ResponseEntity.noContent().build();
    }

}
