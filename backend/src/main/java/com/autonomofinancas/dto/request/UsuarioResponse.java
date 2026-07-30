package com.autonomofinancas.dto.request;

import java.time.OffsetDateTime;

public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private boolean ativo;
    private OffsetDateTime dataCriacao;

    public UsuarioResponse() {
    }

    public UsuarioResponse(
            Long id,
            String nome,
            String email,
            boolean ativo,
            OffsetDateTime dataCriacao) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public OffsetDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(OffsetDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
}
