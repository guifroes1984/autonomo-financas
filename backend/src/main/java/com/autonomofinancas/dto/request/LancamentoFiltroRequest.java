package com.autonomofinancas.dto.request;

import java.time.LocalDate;

import com.autonomofinancas.entity.enums.TipoLancamento;

import io.swagger.v3.oas.annotations.media.Schema;

public class LancamentoFiltroRequest {

    @Schema(description = "Filtra pelo tipo do lançamento.", example = "DESPESA")
    private TipoLancamento tipo;

    @Schema(description = "Filtra pelo identificador da categoria.", example = "1")
    private Long categoriaId;

    @Schema(description = "Filtra pelo identificador da plataforma.", example = "2")
    private Long plataformaId;

    @Schema(description = "Data inicial do período.", example = "2026-08-01")
    private LocalDate inicio;

    @Schema(description = "Data final do período.", example = "2026-08-31")
    private LocalDate fim;

    @Schema(description = "Filtra pela descrição do lançamento.", example = "abastecimento")
    private String descricao;

    public LancamentoFiltroRequest() {
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getPlataformaId() {
        return plataformaId;
    }

    public void setPlataformaId(Long plataformaId) {
        this.plataformaId = plataformaId;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
