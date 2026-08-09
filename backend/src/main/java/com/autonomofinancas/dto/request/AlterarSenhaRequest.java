package com.autonomofinancas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AlterarSenhaRequest {

    @Schema(description = "Senha atual do usuário.", example = "Senha@123")
    @NotBlank(message = "A senha atual é obrigatória.")
    private String senhaAtual;

    @Schema(description = "Nova senha do usuário.", example = "NovaSenha@456")
    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 6, message = "A nova senha deve possuir pelo menos 6 caracteres.")
    private String novaSenha;

    public AlterarSenhaRequest() {
    }

    public AlterarSenhaRequest(
            String senhaAtual,
            String novaSenha) {

        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

}
