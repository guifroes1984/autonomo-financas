package com.autonomofinancas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EsqueciSenhaRequest {

    @Schema(description = "E-mail do usuário.", example = "joao@email.com")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String email;

    public EsqueciSenhaRequest() {
    }

    public EsqueciSenhaRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
