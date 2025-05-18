INSERT INTO perfil_pagamento (cliente_id, status, primeiros_numeros_cartao, ultimos_numeros_cartao, nome_titular_cartao, data_validade)
VALUES
    (1, 'ATIVO', '123456', '7890', 'João Silva', '2026-12-31'),
    (2, 'ATIVO', '654321', '4321', 'Maria Souza', '2027-05-31'),
    (3, 'INATIVO', '111122', '3344', 'Carlos Lima', '2025-08-31');

INSERT INTO mercado_pago_customer (mercado_pago_customer_id, cliente_id, first_name, last_name, email, identification_number, response)
VALUES
    ('mpc_001', 1, 'João', 'Silva', 'joao@example.com', '12345678900', '{}'),
    ('mpc_002', 2, 'Maria', 'Souza', 'maria@example.com', '98765432100', '{}'),
    ('mpc_003', 3, 'Carlos', 'Lima', 'carlos@example.com', '11122233344', '{}');

INSERT INTO mercado_pago_card (
    mercado_pago_card_id, mercado_pago_customer_id, perfil_pagamento_id,
    expiration_month, expiration_year, first_six_digits, last_four_digits,
    cardholder_name, mercado_pago_payment_method, token
)
VALUES
    (1001, 1, 1, 12, 2026, '123456', '7890', 'João Silva', 'visa', 'tok_001'),
    (1002, 2, 2, 5, 2027, '654321', '4321', 'Maria Souza', 'master', 'tok_002'),
    (1003, 3, 3, 8, 2025, '111122', '3344', 'Carlos Lima', 'amex', 'tok_003');

INSERT INTO mercado_pago_payment (
    mercado_pago_payment_id, mercado_pago_customer_id, mercado_pago_card_id,
    operation_type, installments, transaction_amount, transaction_amount_refunded,
    authorized_at, captured_at, payment_method_id, payment_type_id, status,
    status_detail, authorization_code, response
)
VALUES
    (5001, 1, 1, 'regular_payment', 1, 150.00, 0.00, NOW(), NOW(), 'visa', 'credit_card', 'approved', 'accredited', 'AUTH001', '{}'),
    (5002, 2, 2, 'regular_payment', 2, 300.00, 0.00, NOW(), NOW(), 'master', 'credit_card', 'approved', 'accredited', 'AUTH002', '{}'),
    (5003, 3, 3, 'regular_payment', 3, 450.00, 0.00, NOW(), NOW(), 'amex', 'credit_card', 'approved', 'accredited', 'AUTH003', '{}');

INSERT INTO pagamento (
    valor, status, cliente_id, pedido_id, perfil_pagamento_id,
    mercado_pago_payment_id, parcelas, metodo_pagamento,
    authorized_at, captured_at
)
VALUES
    (150.00, 'CONFIRMADO', 1, 'PED001', 1, 1, 1, 'cartao_credito', NOW(), NOW()),
    (300.00, 'CONFIRMADO', 2, 'PED002', 2, 2, 2, 'cartao_credito', NOW(), NOW()),
    (450.00, 'CONFIRMADO', 3, 'PED003', 3, 3, 3, 'cartao_credito', NOW(), NOW());

ALTER SEQUENCE pagamento_id_seq RESTART WITH 4;
ALTER SEQUENCE mercado_pago_payment_id_seq RESTART WITH 4;
ALTER SEQUENCE mercado_pago_card_id_seq RESTART WITH 4;
ALTER SEQUENCE mercado_pago_customer_id_seq RESTART WITH 4;
ALTER SEQUENCE perfil_pagamento_id_seq RESTART WITH 4;