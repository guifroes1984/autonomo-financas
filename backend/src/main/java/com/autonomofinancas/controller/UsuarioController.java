package com.autonomofinancas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.autonomofinancas.dto.request.AlterarSenhaRequest;
import com.autonomofinancas.dto.request.AtualizarUsuarioRequest;
import com.autonomofinancas.dto.request.CriarUsuarioRequest;
import com.autonomofinancas.dto.response.UsuarioPerfilResponse;
import com.autonomofinancas.dto.response.UsuarioResponse;
import com.autonomofinancas.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas ao cadastro e gerenciamento de usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Cadastrar usuário", description = "Realiza o cadastro de um novo usuário no sistema.", security = {})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Já existe um usuário cadastrado com o e-mail informado.", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse criar(@Valid @RequestBody CriarUsuarioRequest request) {
        return usuarioService.criar(request);
    }

    @Operation(summary = "Consultar perfil", description = "Retorna os dados do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil obtido com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<UsuarioPerfilResponse> buscarPerfil() {

        return ResponseEntity.ok(usuarioService.buscarPerfil());

    }

    @Operation(summary = "Atualizar perfil", description = "Atualiza os dados do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Já existe um usuário cadastrado com o e-mail informado.", content = @Content)
    })
    @PutMapping("/me")
    public ResponseEntity<UsuarioPerfilResponse> atualizarPerfil(
            @Valid @RequestBody AtualizarUsuarioRequest request) {

        return ResponseEntity.ok(usuarioService.atualizarPerfil(request));

    }

    @Operation(summary = "Alterar senha", description = "Altera a senha do usuário autenticado após validar a senha atual.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Senha atual incorreta ou dados inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content)
    })
    @PatchMapping("/me/senha")
    public ResponseEntity<Void> alterarSenha(
            @Valid @RequestBody AlterarSenhaRequest request) {

        usuarioService.alterarSenha(request);

        return ResponseEntity.noContent().build();

    }

}
