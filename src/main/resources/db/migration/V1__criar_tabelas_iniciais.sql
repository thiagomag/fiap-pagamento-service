-- Tabela: perfil_pagamento
CREATE TABLE perfil_pagamento
(
    id                       BIGSERIAL PRIMARY KEY,
    cliente_id               BIGINT,
    status                   VARCHAR(255),
    bandeira_cartao          VARCHAR(255),
    primeiros_numeros_cartao VARCHAR(255),
    ultimos_numeros_cartao   VARCHAR(255),
    nome_titular_cartao      VARCHAR(255),
    data_validade            DATE,
    cartao_principal         BOOLEAN
);

-- Tabela: mercado_pago_customer
CREATE TABLE mercado_pago_customer
(
    id                       BIGSERIAL PRIMARY KEY,
    mercado_pago_customer_id VARCHAR(255),
    cliente_id               BIGINT,
    first_name               VARCHAR(255),
    last_name                VARCHAR(255),
    email                    VARCHAR(255),
    identification_number    VARCHAR(255),
    response                 TEXT
);

-- Tabela: mercado_pago_card
CREATE TABLE mercado_pago_card
(
    id                          BIGSERIAL PRIMARY KEY,
    mercado_pago_customer_id    BIGINT NOT NULL,
    perfil_pagamento_id         BIGINT NOT NULL,
    expiration_month            INTEGER,
    expiration_year             INTEGER,
    first_six_digits            VARCHAR(6),
    last_four_digits            VARCHAR(4),
    cardholder_name             VARCHAR(255),
    mercado_pago_payment_method VARCHAR(255),
    token                       VARCHAR(255),
    FOREIGN KEY (mercado_pago_customer_id) REFERENCES mercado_pago_customer (id),
    FOREIGN KEY (perfil_pagamento_id) REFERENCES perfil_pagamento (id)
);

-- Tabela: pagamento
CREATE TABLE pagamento
(
    id                             BIGSERIAL PRIMARY KEY,
    valor                          NUMERIC(19, 2),
    status                         VARCHAR(255),
    cliente_id                     BIGINT,
    pedido_id                      BIGINT,
    perfil_pagamento_id            BIGINT,
    parcelas                       INTEGER,
    metodo_pagamento               VARCHAR(255),
    codigo_autorizacao             VARCHAR(255),
    url_codigo_pix                 TEXT,
    codigo_qr_base64               TEXT,
    codigo_pix                     VARCHAR(255),
    url_boleto                     TEXT,
    codigo_barras_boleto           VARCHAR(255),
    codigo_barras_digitavel_boleto VARCHAR(255),
    authorized_at                  TIMESTAMP,
    captured_at                    TIMESTAMP,
    FOREIGN KEY (perfil_pagamento_id) REFERENCES perfil_pagamento (id)
);

-- Tabela: mercado_pago_payment
CREATE TABLE mercado_pago_payment
(
    id                          BIGSERIAL PRIMARY KEY,
    mercado_pago_customer_id    BIGINT NOT NULL,
    mercado_pago_card_id        BIGINT NOT NULL,
    pagamento_id                BIGINT NOT NULL UNIQUE,
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
    FOREIGN KEY (mercado_pago_customer_id) REFERENCES mercado_pago_customer (id),
    FOREIGN KEY (mercado_pago_card_id) REFERENCES mercado_pago_card (id),
    FOREIGN KEY (pagamento_id) REFERENCES pagamento (id)
);
