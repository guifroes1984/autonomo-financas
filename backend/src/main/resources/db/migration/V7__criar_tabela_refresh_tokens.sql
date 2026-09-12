CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    token_hash VARCHAR(255) NOT NULL,
    data_expiracao TIMESTAMP WITH TIME ZONE NOT NULL,
    revogado BOOLEAN NOT NULL DEFAULT FALSE,
    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_refresh_tokens_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX uk_refresh_tokens_token_hash
    ON refresh_tokens (token_hash);

CREATE INDEX idx_refresh_tokens_usuario_id
    ON refresh_tokens (usuario_id);