package com.autonomofinancas.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens_recuperacao_senha")
public class TokenRecuperacaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false, length = 36, unique = true)
    private String token;

    @Column(nullable = false)
    private OffsetDateTime expiracao;

    @Column(nullable = false)
    private Boolean utilizado;

    @Column(nullable = false)
    private OffsetDateTime dataCriacao;

    public TokenRecuperacaoSenha() {
    }

    public TokenRecuperacaoSenha(Long id, Usuario usuario, String token, OffsetDateTime expiracao, Boolean utilizado,
            OffsetDateTime dataCriacao) {
        this.id = id;
        this.usuario = usuario;
        this.token = token;
        this.expiracao = expiracao;
        this.utilizado = utilizado;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OffsetDateTime getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(OffsetDateTime expiracao) {
        this.expiracao = expiracao;
    }

    public Boolean getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(Boolean utilizado) {
        this.utilizado = utilizado;
    }

    public OffsetDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(OffsetDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean expirado() {
        return OffsetDateTime.now().isAfter(expiracao);
    }

    public boolean disponivel() {
        return !utilizado && !expirado();
    }

}
