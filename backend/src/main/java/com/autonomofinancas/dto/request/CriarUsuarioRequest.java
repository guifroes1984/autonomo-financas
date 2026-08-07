package com.autonomofinancas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CriarUsuarioRequest {

    @Schema(description = "Nome completo do usuário.", example = "João Silva")
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Schema(description = "E-mail do usuário.", example = "joao.silva@email.com")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String email;

    @Schema(description = "Senha de acesso do usuário. Deve possuir no mínimo 6 caracteres.", example = "Senha@123")
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve possuir pelo menos 6 caracteres.")
    private String senha;

    public CriarUsuarioRequest() {
    }

    public CriarUsuarioRequest(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
