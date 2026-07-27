CREATE TABLE plataformas (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_plataformas_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
);

CREATE UNIQUE INDEX uk_plataformas_usuario_nome
    ON plataformas (usuario_id, LOWER(nome));

CREATE INDEX idx_plataformas_usuario
    ON plataformas (usuario_id);