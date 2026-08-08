CREATE TABLE meta (
    id BIGSERIAL PRIMARY KEY,

    usuario_id BIGINT NOT NULL UNIQUE,

    meta_diaria NUMERIC(12,2) NOT NULL,
    meta_mensal NUMERIC(12,2) NOT NULL,

    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_meta_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT ck_meta_diaria_positiva
        CHECK (meta_diaria > 0),

    CONSTRAINT ck_meta_mensal_positiva
        CHECK (meta_mensal > 0)
);