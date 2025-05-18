DROP TABLE IF EXISTS pagamento CASCADE;
DROP TABLE IF EXISTS mercado_pago_payment CASCADE;
DROP TABLE IF EXISTS mercado_pago_card CASCADE;
DROP TABLE IF EXISTS mercado_pago_customer CASCADE;
DROP TABLE IF EXISTS perfil_pagamento CASCADE;

CREATE SEQUENCE IF NOT EXISTS pagamento_id_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS mercado_pago_payment_id_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS mercado_pago_card_id_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS mercado_pago_customer_id_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS perfil_pagamento_id_seq START WITH 1;

-- Tabela: perfil_pagamento
CREATE TABLE perfil_pagamento
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id               BIGINT,
    status                   VARCHAR(255),
    primeiros_numeros_cartao VARCHAR(255),
    ultimos_numeros_cartao   VARCHAR(255),
    nome_titular_cartao      VARCHAR(255),
    data_validade            DATE,
    created_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP,
    deleted_tmsp             TIMESTAMP
);

-- Tabela: mercado_pago_customer
CREATE TABLE mercado_pago_customer
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    mercado_pago_customer_id VARCHAR(255),
    cliente_id               BIGINT,
    first_name               VARCHAR(255),
    last_name                VARCHAR(255),
    email                    VARCHAR(255),
    identification_number    VARCHAR(255),
    response                 TEXT,
    created_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP,
    deleted_tmsp             TIMESTAMP
);

-- Tabela: mercado_pago_card
CREATE TABLE mercado_pago_card
(
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    mercado_pago_card_id        BIGINT NOT NULL,
    mercado_pago_customer_id    BIGINT NOT NULL,
    perfil_pagamento_id         BIGINT NOT NULL,
    expiration_month            INTEGER,
    expiration_year             INTEGER,
    first_six_digits            VARCHAR(6),
    last_four_digits            VARCHAR(4),
    cardholder_name             VARCHAR(255),
    mercado_pago_payment_method VARCHAR(255),
    token                       VARCHAR(255),
    created_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP,
    deleted_tmsp                TIMESTAMP,
    FOREIGN KEY (mercado_pago_customer_id) REFERENCES mercado_pago_customer (id),
    FOREIGN KEY (perfil_pagamento_id) REFERENCES perfil_pagamento (id)
);

-- Tabela: mercado_pago_payment
CREATE TABLE mercado_pago_payment
(
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    mercado_pago_payment_id     BIGINT NOT NULL,
    mercado_pago_customer_id    BIGINT NOT NULL,
    mercado_pago_card_id        BIGINT NOT NULL,
    operation_type              VARCHAR(255),
    installments                INTEGER,
    transaction_amount          NUMERIC(19, 2),
    transaction_amount_refunded NUMERIC(19, 2),
    authorized_at               TIMESTAMP,
    captured_at                 TIMESTAMP,
    payment_method_id           VARCHAR(255),
    payment_type_id             VARCHAR(255),
    status                      VARCHAR(255),
    status_detail               VARCHAR(255),
    authorization_code          VARCHAR(255),
    response                    TEXT,
    created_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP,
    deleted_tmsp                TIMESTAMP,
    FOREIGN KEY (mercado_pago_customer_id) REFERENCES mercado_pago_customer (id),
    FOREIGN KEY (mercado_pago_card_id) REFERENCES mercado_pago_card (id)
);

-- Tabela: pagamento
CREATE TABLE pagamento
(
    id                             BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor                          NUMERIC(19, 2),
    status                         VARCHAR(255),
    cliente_id                     BIGINT,
    pedido_id                      VARCHAR(255),
    perfil_pagamento_id            BIGINT,
    mercado_pago_payment_id        BIGINT,
    parcelas                       INTEGER,
    metodo_pagamento               VARCHAR(255),
    authorized_at                  TIMESTAMP,
    captured_at                    TIMESTAMP,
    created_at                     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                     TIMESTAMP,
    deleted_tmsp                   TIMESTAMP,
    FOREIGN KEY (perfil_pagamento_id) REFERENCES perfil_pagamento (id),
    FOREIGN KEY (mercado_pago_payment_id) REFERENCES mercado_pago_payment (id)
);



