package com.autonomofinancas.dto.request;

import com.autonomofinancas.entity.enums.TipoCategoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CategoriaRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotNull(message = "O tipo é obrigatório.")
    private TipoCategoria tipo;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "A cor deve estar no formato hexadecimal (#RRGGBB).")
    private String cor;

    public CategoriaRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

}
