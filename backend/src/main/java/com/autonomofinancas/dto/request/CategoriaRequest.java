package com.autonomofinancas.dto.request;

import com.autonomofinancas.entity.enums.TipoCategoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CategoriaRequest {

    @Schema(description = "Nome da categoria.", example = "Combustível")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @Schema(description = "Tipo da categoria.", example = "DESPESA")
    @NotNull(message = "O tipo é obrigatório.")
    private TipoCategoria tipo;

    @Schema(description = "Cor da categoria no formato hexadecimal (#RRGGBB).", example = "#FF9800")
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
