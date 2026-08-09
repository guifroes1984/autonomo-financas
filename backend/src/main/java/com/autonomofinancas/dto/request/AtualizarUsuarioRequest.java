package com.autonomofinancas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AtualizarUsuarioRequest {

    @Schema(description = "Nome completo do usuário.", example = "João Silva")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
    private String nome;

    @Schema(description = "E-mail do usuário.", example = "joao.silva@email.com")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String email;

    public AtualizarUsuarioRequest() {
    }

    public AtualizarUsuarioRequest(
            @NotBlank(message = "O nome é obrigatório.") @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.") String nome,
            @NotBlank(message = "O e-mail é obrigatório.") @Email(message = "Informe um e-mail válido.") String email) {
        this.nome = nome;
        this.email = email;
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

    

}
