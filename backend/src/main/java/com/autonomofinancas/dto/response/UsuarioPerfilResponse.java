package com.autonomofinancas.dto.response;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioPerfilResponse {

    @Schema(description = "Identificador do usuário.", example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário.", example = "João Silva")
    private String nome;

    @Schema(description = "E-mail do usuário.", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Indica se o usuário está ativo.", example = "true")
    private Boolean ativo;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataAtualizacao;

    public UsuarioPerfilResponse() {
    }

    public UsuarioPerfilResponse(Long id, String nome, String email, Boolean ativo, OffsetDateTime dataCriacao,
            OffsetDateTime dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public OffsetDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(OffsetDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public OffsetDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(OffsetDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
