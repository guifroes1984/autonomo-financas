CREATE TABLE lancamentos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    plataforma_id BIGINT,
    tipo VARCHAR(20) NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    valor NUMERIC(12,2) NOT NULL,
    data_lancamento DATE NOT NULL,
    observacao VARCHAR(500),
    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_lancamentos_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id),

    CONSTRAINT fk_lancamentos_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categorias (id),

    CONSTRAINT fk_lancamentos_plataforma
        FOREIGN KEY (plataforma_id)
        REFERENCES plataformas (id),

    CONSTRAINT ck_lancamentos_tipo
        CHECK (tipo IN ('RECEITA', 'DESPESA')),

    CONSTRAINT ck_lancamentos_valor_positivo
        CHECK (valor > 0)
);

CREATE INDEX idx_lancamentos_usuario
    ON lancamentos (usuario_id);

CREATE INDEX idx_lancamentos_categoria
    ON lancamentos (categoria_id);

CREATE INDEX idx_lancamentos_plataforma
    ON lancamentos (plataforma_id);

CREATE INDEX idx_lancamentos_usuario_data
    ON lancamentos (
        usuario_id,
        data_lancamento
    );

CREATE INDEX idx_lancamentos_usuario_tipo_data
    ON lancamentos (
        usuario_id,
        tipo,
        data_lancamento
    );