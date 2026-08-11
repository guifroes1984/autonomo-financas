package com.autonomofinancas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RedefinirSenhaRequest {

    @Schema(description = "Token recebido por e-mail.", example = "3d1c84c4-f21d-4f7d-a7b2-f29f4a1ddc95")
    @NotBlank(message = "O token é obrigatório.")
    private String token;

    @Schema(description = "Nova senha do usuário.", example = "NovaSenha@123")
    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 6, message = "A nova senha deve possuir pelo menos 6 caracteres.")
    private String novaSenha;

    public RedefinirSenhaRequest() {
    }

    public RedefinirSenhaRequest(
            String token,
            String novaSenha) {

        this.token = token;
        this.novaSenha = novaSenha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

}
