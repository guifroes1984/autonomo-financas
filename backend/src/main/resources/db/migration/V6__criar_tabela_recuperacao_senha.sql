CREATE TABLE tokens_recuperacao_senha (

    id BIGSERIAL PRIMARY KEY,

    usuario_id BIGINT NOT NULL,

    token VARCHAR(100) NOT NULL,

    expiracao TIMESTAMP WITH TIME ZONE NOT NULL,

    utilizado BOOLEAN NOT NULL DEFAULT FALSE,

    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_tokens_recuperacao_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
);

CREATE UNIQUE INDEX uk_tokens_recuperacao_token
    ON tokens_recuperacao_senha (token);

CREATE INDEX idx_tokens_recuperacao_usuario
    ON tokens_recuperacao_senha (usuario_id);

CREATE INDEX idx_tokens_recuperacao_expiracao
    ON tokens_recuperacao_senha (expiracao);