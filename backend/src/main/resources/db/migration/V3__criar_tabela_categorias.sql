CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    cor VARCHAR(7),
    ativa BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_categorias_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id),

    CONSTRAINT ck_categorias_tipo
        CHECK (tipo IN ('RECEITA', 'DESPESA')),

    CONSTRAINT ck_categorias_cor
        CHECK (
            cor IS NULL
            OR cor ~ '^#[0-9A-Fa-f]{6}$'
        )
);

CREATE UNIQUE INDEX uk_categorias_usuario_nome_tipo
    ON categorias (
        usuario_id,
        LOWER(nome),
        tipo
    );

CREATE INDEX idx_categorias_usuario
    ON categorias (usuario_id);

CREATE INDEX idx_categorias_usuario_tipo_ativa
    ON categorias (
        usuario_id,
        tipo,
        ativa
    );