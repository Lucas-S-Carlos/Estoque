
CREATE DATABASE IF NOT EXISTS estoque
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE estoque;

-- 1. Produtos
CREATE TABLE produto (
    codigo_prod VARCHAR(100) PRIMARY KEY,
    nome_prod VARCHAR(255) NOT NULL,
    categoria_prod VARCHAR(150),
    descricao_prod TEXT,
    modelo_prod VARCHAR(150),
    peso_prod DECIMAL(12,3),
    preco_prod DECIMAL(12,2),
    estoque_prod_min INT NOT NULL DEFAULT 0,
    estoque_prod_max INT NOT NULL DEFAULT 0,
    status_prod VARCHAR(20),
    CONSTRAINT chk_prod_peso CHECK (peso_prod IS NULL OR peso_prod >= 0),
    CONSTRAINT chk_prod_preco CHECK (preco_prod IS NULL OR preco_prod >= 0),
    CONSTRAINT chk_prod_min CHECK (estoque_prod_min >= 0),
    CONSTRAINT chk_prod_max CHECK (estoque_prod_max >= 0),
    CONSTRAINT chk_prod_min_max CHECK (estoque_prod_max >= estoque_prod_min)
);

-- 2. Variacoes/SKUs
CREATE TABLE variacao_produto (
    sku VARCHAR(100) PRIMARY KEY,
    codigo_prod VARCHAR(100) NOT NULL,
    tamanho_prod_var VARCHAR(100),
    cor_prod_var VARCHAR(100),
    modelo_prod_var VARCHAR(150),
    quantidade_prod INT NOT NULL DEFAULT 0,
    preco_prod_var DECIMAL(12,2),
    CONSTRAINT fk_variacao_produto
      FOREIGN KEY (codigo_prod) REFERENCES produto(codigo_prod),
    CONSTRAINT chk_variacao_quantidade CHECK (quantidade_prod >= 0),
    CONSTRAINT chk_variacao_preco CHECK (preco_prod_var IS NULL OR preco_prod_var >= 0)
);

-- 3. Kits
CREATE TABLE kit (
    codigo_kit VARCHAR(100) PRIMARY KEY,
    nome_kit VARCHAR(255) NOT NULL,
    tipo_kit VARCHAR(50),
    quantidade_kit INT NOT NULL DEFAULT 0,
    CONSTRAINT chk_kit_quantidade CHECK (quantidade_kit >= 0)
);

CREATE TABLE kit_componente (
    codigo_kit VARCHAR(100) NOT NULL,
    codigo_prod VARCHAR(100),
    sku VARCHAR(100),
    quantidade_componente INT NOT NULL,
    CONSTRAINT pk_kit_componente PRIMARY KEY (codigo_kit, codigo_prod, sku),
    CONSTRAINT fk_kit_componente_kit
      FOREIGN KEY (codigo_kit) REFERENCES kit(codigo_kit),
    CONSTRAINT fk_kit_componente_produto
      FOREIGN KEY (codigo_prod) REFERENCES produto(codigo_prod),
    CONSTRAINT fk_kit_componente_sku
      FOREIGN KEY (sku) REFERENCES variacao_produto(sku),
    CONSTRAINT chk_kit_componente_quantidade CHECK (quantidade_componente > 0),
    CONSTRAINT chk_kit_componente_item CHECK (
      (codigo_prod IS NOT NULL AND sku IS NULL) OR
      (codigo_prod IS NULL AND sku IS NOT NULL)
    )
);

-- 4. Movimentacoes de entrada e saida
CREATE TABLE movimentacao_estoque (
    id_movimentacao BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_prod VARCHAR(100) NOT NULL,
    sku VARCHAR(100),
    codigo_kit VARCHAR(100),
    tipo_mov_es VARCHAR(20) NOT NULL,
    quantidade_mov_es INT NOT NULL,
    motivo_mov_es VARCHAR(100),
    doc_mov_es VARCHAR(255),
    origem_mov_es VARCHAR(255),
    destino_mov_es VARCHAR(255),
    responsavel_mov_es VARCHAR(255) NOT NULL,
    obs_mov_es TEXT,
    data_hora_movimentacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mov_produto FOREIGN KEY (codigo_prod) REFERENCES produto(codigo_prod),
    CONSTRAINT fk_mov_sku FOREIGN KEY (sku) REFERENCES variacao_produto(sku),
    CONSTRAINT fk_mov_kit FOREIGN KEY (codigo_kit) REFERENCES kit(codigo_kit),
    CONSTRAINT chk_mov_tipo CHECK (tipo_mov_es IN ('Entrada', 'Saida', 'Saída')),
    CONSTRAINT chk_mov_quantidade CHECK (quantidade_mov_es > 0),
    CONSTRAINT chk_mov_item CHECK (
      (sku IS NULL AND codigo_kit IS NULL) OR
      (sku IS NOT NULL AND codigo_kit IS NULL) OR
      (sku IS NULL AND codigo_kit IS NOT NULL)
    )
);

-- 5. Lotes e numeros de serie
CREATE TABLE lote_serie (
    id_lote_serie BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_prod VARCHAR(100) NOT NULL,
    lote_mov_lote INT,
    dt_fab_mov_lote DATE,
    fornecedor_mov_lote VARCHAR(255),
    tipo_mov_lote VARCHAR(100),
    numero_serie_mov_lote INT UNIQUE,
    dt_val_mov_lote DATE,
    CONSTRAINT fk_lote_produto FOREIGN KEY (codigo_prod) REFERENCES produto(codigo_prod),
    CONSTRAINT chk_lote_datas CHECK (
      dt_fab_mov_lote IS NULL OR dt_val_mov_lote IS NULL
      OR dt_val_mov_lote >= dt_fab_mov_lote
    )
);

-- 6. Multilocalizacao e transferencias
CREATE TABLE movimentacao_multilocalizacao (
    id_mov_mult BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_prod VARCHAR(100) NOT NULL,
    nome_prod_mov_mult VARCHAR(255),
    origem_mov_mult VARCHAR(255) NOT NULL,
    prod_mov_mult VARCHAR(255),
    id_lote_serie BIGINT,
    lote_mov_mult INT,
    doc_mov_mult VARCHAR(255),
    destino_mov_mult VARCHAR(255) NOT NULL,
    quantidade_mov_mult INT NOT NULL,
    numero_serie_mov_mult INT,
    obs_mov_mult TEXT,
    responsavel_mov_mult VARCHAR(255),
    data_hora_mov_mult DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mult_produto FOREIGN KEY (codigo_prod) REFERENCES produto(codigo_prod),
    CONSTRAINT fk_mult_lote FOREIGN KEY (id_lote_serie) REFERENCES lote_serie(id_lote_serie),
    CONSTRAINT chk_mult_quantidade CHECK (quantidade_mov_mult > 0),
    CONSTRAINT chk_mult_origem_destino CHECK (origem_mov_mult <> destino_mov_mult)
);

-- 7. Inventario e ajustes
CREATE TABLE inventario (
    id_inventario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_inv VARCHAR(255) NOT NULL,
    descricao_inv TEXT,
    quantidade_estoque_inv INT,
    quantidade_inv INT,
    codigo_prod VARCHAR(100),
    data_hora_inventario DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    responsavel_inv VARCHAR(255),
    CONSTRAINT fk_inventario_produto FOREIGN KEY (codigo_prod) REFERENCES produto(codigo_prod),
    CONSTRAINT chk_inventario_qtd_estoque CHECK (
      quantidade_estoque_inv IS NULL OR quantidade_estoque_inv >= 0
    ),
    CONSTRAINT chk_inventario_qtd CHECK (quantidade_inv IS NULL OR quantidade_inv >= 0)
);

CREATE TABLE ajuste_estoque (
    id_ajuste BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_prod VARCHAR(100) NOT NULL,
    sku VARCHAR(100),
    item_inv_ajustes VARCHAR(255),
    descricao_inv_ajustes TEXT,
    quantidade_inv_ajustes INT NOT NULL,
    preco_inv_ajustes DECIMAL(12,2),
    preco_total_inv_ajustes DECIMAL(12,2),
    status_inv_ajustes VARCHAR(50),
    dt_val_inv_ajustes DATE,
    quantidade_anterior INT,
    quantidade_posterior INT,
    diferenca_quantidade INT,
    motivo VARCHAR(100),
    observacao TEXT,
    responsavel VARCHAR(255),
    data_hora_ajuste DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_lote_serie BIGINT,
    CONSTRAINT fk_ajuste_produto FOREIGN KEY (codigo_prod) REFERENCES produto(codigo_prod),
    CONSTRAINT fk_ajuste_sku FOREIGN KEY (sku) REFERENCES variacao_produto(sku),
    CONSTRAINT fk_ajuste_lote FOREIGN KEY (id_lote_serie) REFERENCES lote_serie(id_lote_serie),
    CONSTRAINT chk_ajuste_quantidade CHECK (quantidade_inv_ajustes >= 0),
    CONSTRAINT chk_ajuste_preco CHECK (preco_inv_ajustes IS NULL OR preco_inv_ajustes >= 0),
    CONSTRAINT chk_ajuste_total CHECK (preco_total_inv_ajustes IS NULL OR preco_total_inv_ajustes >= 0)
);

-- 8. Relatorio/sugestao MRP
CREATE TABLE relatorio_mrp (
    id_relatorio_mrp BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_prod VARCHAR(100) NOT NULL,
    nome_prod_relatorio_mrp VARCHAR(255),
    categoria_relatorio_mrp VARCHAR(150),
    status_relatorio_mrp VARCHAR(50),
    sugestao_relatorio_mrp TEXT,
    data_prev_repo_relatorio_mrp DATE,
    responsavel_relatorio_mrp VARCHAR(255),
    justificativa_relatorio_mrp TEXT,
    quantidade_sugerida INT,
    data_hora_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    responsavel_decisao VARCHAR(255),
    data_hora_decisao DATETIME,
    decisao VARCHAR(30),
    CONSTRAINT fk_mrp_produto FOREIGN KEY (codigo_prod) REFERENCES produto(codigo_prod),
    CONSTRAINT chk_mrp_quantidade CHECK (quantidade_sugerida IS NULL OR quantidade_sugerida > 0),
    CONSTRAINT chk_mrp_decisao CHECK (
      decisao IS NULL OR decisao IN ('Aprovada', 'Recusada', 'Aprovado', 'Recusado')
    )
);

-- ============================================================
-- PROCEDURES, VIEWS E TRIGGERS PARA PRODUTO
-- ============================================================

-- Procedure para inserir produto
DELIMITER //
CREATE PROCEDURE inserir_produto(
    IN p_codigo_prod VARCHAR(100),
    IN p_nome_prod VARCHAR(255),
    IN p_categoria_prod VARCHAR(150),
    IN p_descricao_prod TEXT,
    IN p_modelo_prod VARCHAR(150),
    IN p_peso_prod DECIMAL(12,3),
    IN p_preco_prod DECIMAL(12,2),
    IN p_estoque_min INT,
    IN p_estoque_max INT,
    IN p_status_prod VARCHAR(20)
)
BEGIN
    IF p_peso_prod < 0 OR p_preco_prod < 0 OR p_estoque_min < 0 OR p_estoque_max < p_estoque_min THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Dados inválidos para produto';
    END IF;

    INSERT INTO produto (codigo_prod, nome_prod, categoria_prod, descricao_prod, modelo_prod, peso_prod, preco_prod, estoque_prod_min, estoque_prod_max, status_prod)
    VALUES (p_codigo_prod, p_nome_prod, p_categoria_prod, p_descricao_prod, p_modelo_prod, p_peso_prod, p_preco_prod, p_estoque_min, p_estoque_max, p_status_prod);
END //
DELIMITER ;

-- View de produtos ativos/inativos (O filtro de ativo ou inativo é definido no dao de produto)
CREATE VIEW vw_produtos AS
SELECT codigo_prod, nome_prod, categoria_prod, preco_prod, estoque_prod_min, estoque_prod_max, status_prod
FROM produto;



-- ============================================================
-- PROCEDURES, VIEWS E TRIGGERS PARA VARIAÇÃO DE PRODUTO
-- ============================================================

-- Procedure para inserir variação
DELIMITER //
CREATE PROCEDURE inserir_variacao_produto(
    IN p_sku VARCHAR(100),
    IN p_codigo_prod VARCHAR(100),
    IN p_tamanho VARCHAR(100),
    IN p_cor VARCHAR(100),
    IN p_modelo VARCHAR(150),
    IN p_quantidade INT,
    IN p_preco DECIMAL(12,2)
)
BEGIN
    IF p_quantidade < 0 OR p_preco < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Dados inválidos para variação';
    END IF;

    INSERT INTO variacao_produto (sku, codigo_prod, tamanho_prod_var, cor_prod_var, modelo_prod_var, quantidade_prod, preco_prod_var)
    VALUES (p_sku, p_codigo_prod, p_tamanho, p_cor, p_modelo, p_quantidade, p_preco);
END //
DELIMITER ;

-- View de variações por produto
CREATE VIEW vw_variacoes_produto AS
SELECT sku, codigo_prod, tamanho_prod_var, cor_prod_var, modelo_prod_var, quantidade_prod, preco_prod_var
FROM variacao_produto;


-- ============================================================
-- PROCEDURES, VIEWS E TRIGGERS PARA KIT
-- ============================================================

-- Procedure para inserir kit
DELIMITER //
CREATE PROCEDURE inserir_kit(
    IN p_codigo_kit VARCHAR(100),
    IN p_nome_kit VARCHAR(255),
    IN p_tipo_kit VARCHAR(50),
    IN p_quantidade INT
)
BEGIN
    IF p_quantidade < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade inválida para kit';
    END IF;

    INSERT INTO kit (codigo_kit, nome_kit, tipo_kit, quantidade_kit)
    VALUES (p_codigo_kit, p_nome_kit, p_tipo_kit, p_quantidade);
END //
DELIMITER ;

-- Procedure para inserir componente de kit
DELIMITER //
CREATE PROCEDURE inserir_kit_componente(
    IN p_codigo_kit VARCHAR(100),
    IN p_codigo_prod VARCHAR(100),
    IN p_sku VARCHAR(100),
    IN p_quantidade INT
)
BEGIN
    IF p_quantidade <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade inválida para componente';
    END IF;

    INSERT INTO kit_componente (codigo_kit, codigo_prod, sku, quantidade_componente)
    VALUES (p_codigo_kit, p_codigo_prod, p_sku, p_quantidade);
END //
DELIMITER ;

-- View de kits com componentes
CREATE VIEW vw_kits_com_componentes AS
SELECT k.codigo_kit, k.nome_kit, k.tipo_kit, kc.codigo_prod, kc.sku, kc.quantidade_componente
FROM kit k
JOIN kit_componente kc ON k.codigo_kit = kc.codigo_kit;


-- ============================================================
-- PROCEDURES, VIEWS E TRIGGERS PARA RELATÓRIO MRP
-- ============================================================

-- Procedure para realizar saída de produto
DELIMITER //
CREATE PROCEDURE realizar_saida_produto(IN p_codigo_prod VARCHAR(100), IN p_qtd INT)
BEGIN
    DECLARE estoque_atual INT;

    SELECT estoque_prod_max INTO estoque_atual FROM produto WHERE codigo_prod = p_codigo_prod;

    IF estoque_atual >= p_qtd THEN
        UPDATE produto SET estoque_prod_max = estoque_prod_max - p_qtd WHERE codigo_prod = p_codigo_prod;
        INSERT INTO movimentacao_estoque (codigo_prod, tipo_mov_es, quantidade_mov_es, responsavel_mov_es)
        VALUES (p_codigo_prod, 'Saida', p_qtd, 'Sistema');
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente';
    END IF;
END //
DELIMITER ;

-- View de produtos com estoque baixo
CREATE VIEW vw_produtos_estoque_baixo AS
SELECT codigo_prod, nome_prod, estoque_prod_min, estoque_prod_max
FROM produto
WHERE estoque_prod_max < estoque_prod_min;

-- Trigger para movimentação ao aprovar sugestão MRP
DELIMITER //
CREATE TRIGGER trg_movimentacao_mrp_aprovado
AFTER UPDATE ON relatorio_mrp
FOR EACH ROW
BEGIN
    IF NEW.decisao = 'Aprovada' THEN
        INSERT INTO movimentacao_estoque (codigo_prod, tipo_mov_es, quantidade_mov_es, responsavel_mov_es)
        VALUES (NEW.codigo_prod, 'Saida', NEW.quantidade_sugerida, NEW.responsavel_decisao);
    END IF;
END //
DELIMITER ;


-- ============================================================
-- PROCEDURES, VIEWS E TRIGGERS PARA PEDIDO DE COMPRA
-- ============================================================

-- Procedure para criar pedido de compra
DELIMITER //
CREATE PROCEDURE criar_pedido_compra(
    IN p_id_relatorio BIGINT,
    IN p_codigo_prod VARCHAR(100),
    IN p_qtd INT,
    IN p_fornecedor VARCHAR(255),
    IN p_responsavel VARCHAR(255)
)
BEGIN
    IF p_qtd <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade inválida';
    END IF;

    INSERT INTO pedido_compra (id_relatorio_mrp, codigo_prod, quantidade_pedido, fornecedor, status_pedido, data_pedido, responsavel)
    VALUES (p_id_relatorio, p_codigo_prod, p_qtd, p_fornecedor, 'Pendente', CURRENT_DATE, p_responsavel);
END //
DELIMITER ;

-- View de pedidos de compra
CREATE VIEW vw_pedidos_compra AS
SELECT id_pedido, id_relatorio_mrp, codigo_prod, quantidade_pedido, fornecedor, status_pedido, data_pedido, responsavel
FROM pedido_compra;

-- Trigger para movimentação ao receber pedido
DELIMITER //
CREATE TRIGGER trg_movimentacao_pedido_recebido
AFTER UPDATE ON pedido_compra
FOR EACH ROW
BEGIN
    IF NEW.status_pedido = 'Recebido' THEN
        INSERT INTO movimentacao_estoque (codigo_prod, tipo_mov_es, quantidade_mov_es, responsavel_mov_es)
        VALUES (NEW.codigo_prod, 'Entrada', NEW.quantidade_pedido, NEW.responsavel_decisao);
    END IF;
END //
DELIMITER ;


-- ============================================================
-- PROCEDURE E VIEWS PARA MOVIMENTACAO DE ESTOQUE
-- ============================================================

DELIMITER //

-- Procedure: registrar_movimentacao_estoque
DROP PROCEDURE IF EXISTS registrar_movimentacao_estoque;
//
CREATE PROCEDURE registrar_movimentacao_estoque(
    IN p_codigo_prod VARCHAR(100),
    IN p_sku VARCHAR(100),
    IN p_codigo_kit VARCHAR(100),
    IN p_tipo_mov_es VARCHAR(20),
    IN p_quantidade_mov_es INT,
    IN p_motivo_mov_es VARCHAR(100),
    IN p_doc_mov_es VARCHAR(255),
    IN p_origem_mov_es VARCHAR(255),
    IN p_destino_mov_es VARCHAR(255),
    IN p_responsavel_mov_es VARCHAR(255),
    IN p_obs_mov_es TEXT
)
BEGIN
    DECLARE v_tipo_normalizado VARCHAR(20);
    DECLARE v_saldo_atual INT;
    DECLARE v_comp_sku VARCHAR(100);
    DECLARE v_comp_qtd INT;
    DECLARE done INT DEFAULT 0;

    IF p_quantidade_mov_es IS NULL OR p_quantidade_mov_es <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade inválida (deve ser > 0)';
    END IF;

    SET v_tipo_normalizado = p_tipo_mov_es;

    IF v_tipo_normalizado NOT IN ('Entrada', 'Saida', 'Saída') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Tipo de movimentação inválido';
    END IF;

    IF NOT (
        (p_sku IS NULL AND p_codigo_kit IS NULL) OR
        (p_sku IS NOT NULL AND p_codigo_kit IS NULL) OR
        (p_sku IS NULL AND p_codigo_kit IS NOT NULL)
    ) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Movimentação deve referenciar SKU ou KIT ou apenas produto';
    END IF;

    INSERT INTO movimentacao_estoque (
        codigo_prod, sku, codigo_kit, tipo_mov_es, quantidade_mov_es,
        motivo_mov_es, doc_mov_es, origem_mov_es, destino_mov_es,
        responsavel_mov_es, obs_mov_es, data_hora_movimentacao
    ) VALUES (
        p_codigo_prod, p_sku, p_codigo_kit, p_tipo_mov_es, p_quantidade_mov_es,
        p_motivo_mov_es, p_doc_mov_es, p_origem_mov_es, p_destino_mov_es,
        p_responsavel_mov_es, p_obs_mov_es, NOW()
    );

    IF p_sku IS NOT NULL THEN
        SELECT quantidade_prod INTO v_saldo_atual FROM variacao_produto WHERE sku = p_sku FOR UPDATE;
        IF v_saldo_atual IS NULL THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'SKU não encontrado';
        END IF;

        IF v_tipo_normalizado = 'Entrada' THEN
            UPDATE variacao_produto
            SET quantidade_prod = quantidade_prod + p_quantidade_mov_es
            WHERE sku = p_sku;
        ELSE
            IF v_saldo_atual < p_quantidade_mov_es THEN
                SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente para SKU';
            END IF;
            UPDATE variacao_produto
            SET quantidade_prod = quantidade_prod - p_quantidade_mov_es
            WHERE sku = p_sku;
        END IF;
    END IF;

    IF p_codigo_kit IS NOT NULL THEN
        SELECT quantidade_kit INTO v_saldo_atual FROM kit WHERE codigo_kit = p_codigo_kit FOR UPDATE;
        IF v_saldo_atual IS NULL THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Kit não encontrado';
        END IF;

        IF v_tipo_normalizado = 'Entrada' THEN
            UPDATE kit SET quantidade_kit = quantidade_kit + p_quantidade_mov_es WHERE codigo_kit = p_codigo_kit;
        ELSE
            IF v_saldo_atual < p_quantidade_mov_es THEN
                SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente para Kit';
            END IF;
            UPDATE kit SET quantidade_kit = quantidade_kit - p_quantidade_mov_es WHERE codigo_kit = p_codigo_kit;

            DECLARE cur_comp CURSOR FOR
                SELECT sku, quantidade_componente FROM kit_componente WHERE codigo_kit = p_codigo_kit AND sku IS NOT NULL;
            DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

            OPEN cur_comp;
            read_loop: LOOP
                FETCH cur_comp INTO v_comp_sku, v_comp_qtd;
                IF done = 1 THEN
                    LEAVE read_loop;
                END IF;

                SELECT quantidade_prod INTO v_saldo_atual FROM variacao_produto WHERE sku = v_comp_sku FOR UPDATE;
                IF v_saldo_atual IS NULL THEN
                    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = CONCAT('Componente SKU não encontrado: ', v_comp_sku);
                END IF;

                IF v_saldo_atual < (v_comp_qtd * p_quantidade_mov_es) THEN
                    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = CONCAT('Estoque insuficiente para componente SKU: ', v_comp_sku);
                END IF;

                UPDATE variacao_produto
                SET quantidade_prod = quantidade_prod - (v_comp_qtd * p_quantidade_mov_es)
                WHERE sku = v_comp_sku;
            END LOOP;
            CLOSE cur_comp;
        END IF;
    END IF;

    -- Observação: movimentações que referenciam apenas codigo_prod (sem sku e sem kit)
    -- são registradas, mas o schema atual não define saldo agregado em produto, portanto não atualizamos produto aqui.

END;
//
DELIMITER ;

-- ============================================================
-- Views de consulta
-- ============================================================

DROP VIEW IF EXISTS vw_movimentacoes;
CREATE VIEW vw_movimentacoes AS
SELECT
    m.id_movimentacao,
    m.codigo_prod,
    p.nome_prod,
    m.sku,
    v.tamanho_prod_var,
    v.cor_prod_var,
    v.modelo_prod_var,
    m.codigo_kit,
    k.nome_kit,
    m.tipo_mov_es,
    m.quantidade_mov_es,
    m.motivo_mov_es,
    m.doc_mov_es,
    m.origem_mov_es,
    m.destino_mov_es,
    m.responsavel_mov_es,
    m.obs_mov_es,
    m.data_hora_movimentacao
FROM movimentacao_estoque m
LEFT JOIN produto p ON m.codigo_prod = p.codigo_prod
LEFT JOIN variacao_produto v ON m.sku = v.sku
LEFT JOIN kit k ON m.codigo_kit = k.codigo_kit;

DROP VIEW IF EXISTS vw_movimentacoes_resumo;
CREATE VIEW vw_movimentacoes_resumo AS
SELECT
    COALESCE(m.sku, m.codigo_prod) AS item,
    m.tipo_mov_es,
    SUM(m.quantidade_mov_es) AS total_qtd,
    MIN(m.data_hora_movimentacao) AS primeira_mov,
    MAX(m.data_hora_movimentacao) AS ultima_mov
FROM movimentacao_estoque m
GROUP BY COALESCE(m.sku, m.codigo_prod), m.tipo_mov_es;

DROP VIEW IF EXISTS vw_saldo_sku;
CREATE VIEW vw_saldo_sku AS
SELECT sku, codigo_prod, quantidade_prod AS saldo_atual, preco_prod_var
FROM variacao_produto;

-- ============================================================
-- PROCEDURES E VIEWS PARA LOTE / NUMERO DE SERIE
-- ============================================================

DELIMITER //

-- Procedure: inserir_lote_serie
DROP PROCEDURE IF EXISTS inserir_lote_serie;
//
CREATE PROCEDURE inserir_lote_serie(
    IN p_codigo_prod VARCHAR(100),
    IN p_lote VARCHAR(100),
    IN p_numero_serie VARCHAR(100),
    IN p_data_fabricacao DATE,
    IN p_data_validade DATE,
    IN p_fornecedor VARCHAR(255)
)
BEGIN

    IF p_lote IS NULL OR p_lote = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Lote obrigatório';
    END IF;

    IF p_data_fabricacao IS NOT NULL AND p_data_validade IS NOT NULL THEN
        IF p_data_validade < p_data_fabricacao THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Data de validade anterior à data de fabricação';
        END IF;
    END IF;


    IF NOT EXISTS (SELECT 1 FROM produto WHERE codigo_prod = p_codigo_prod) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Produto referenciado não existe';
    END IF;

    INSERT INTO lote_serie (codigo_prod, lote, numero_serie, data_fabricacao, data_validade, fornecedor)
    VALUES (p_codigo_prod, p_lote, p_numero_serie, p_data_fabricacao, p_data_validade, p_fornecedor);
END;
//
DELIMITER ;

-- Procedure: atualizar_lote_serie
DELIMITER //
DROP PROCEDURE IF EXISTS atualizar_lote_serie;
//
CREATE PROCEDURE atualizar_lote_serie(
    IN p_id_lote_serie BIGINT,
    IN p_lote VARCHAR(100),
    IN p_numero_serie VARCHAR(100),
    IN p_data_fabricacao DATE,
    IN p_data_validade DATE,
    IN p_fornecedor VARCHAR(255)
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM lote_serie WHERE id_lote_serie = p_id_lote_serie) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Lote/serie não encontrado';
    END IF;

    IF p_data_fabricacao IS NOT NULL AND p_data_validade IS NOT NULL THEN
        IF p_data_validade < p_data_fabricacao THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Data de validade anterior à data de fabricação';
        END IF;
    END IF;

    UPDATE lote_serie
    SET lote = p_lote,
        numero_serie = p_numero_serie,
        data_fabricacao = p_data_fabricacao,
        data_validade = p_data_validade,
        fornecedor = p_fornecedor
    WHERE id_lote_serie = p_id_lote_serie;
END;
//
DELIMITER ;

-- View: vw_lotes_serie (para consultas nas telas)
DROP VIEW IF EXISTS vw_lotes_serie;
CREATE VIEW vw_lotes_serie AS
SELECT
    ls.id_lote_serie,
    ls.codigo_prod,
    p.nome_prod,
    ls.lote,
    ls.numero_serie,
    ls.data_fabricacao,
    ls.data_validade,
    ls.fornecedor
FROM lote_serie ls
LEFT JOIN produto p ON ls.codigo_prod = p.codigo_prod;

-- View: vw_lotes_vencendo (lotes com validade próxima ou vencida)
DROP VIEW IF EXISTS vw_lotes_vencendo;
CREATE VIEW vw_lotes_vencendo AS
SELECT
    id_lote_serie,
    codigo_prod,
    lote,
    numero_serie,
    data_fabricacao,
    data_validade,
    fornecedor,
    DATEDIFF(data_validade, CURRENT_DATE) AS dias_para_vencimento
FROM lote_serie
WHERE data_validade IS NOT NULL;

-- ============================================================
-- PROCEDURE E VIEWS PARA MOVIMENTACAO MULTILOCALIZACAO
-- ============================================================

DELIMITER //

-- Procedure: registrar_transferencia_multilocalizacao
DROP PROCEDURE IF EXISTS registrar_transferencia_multilocalizacao;
//
CREATE PROCEDURE registrar_transferencia_multilocalizacao(
    IN p_codigo_prod VARCHAR(100),
    IN p_sku VARCHAR(100),
    IN p_codigo_kit VARCHAR(100),
    IN p_origem_local VARCHAR(255),
    IN p_destino_local VARCHAR(255),
    IN p_quantidade INT,
    IN p_motivo VARCHAR(255),
    IN p_doc_referencia VARCHAR(255),
    IN p_responsavel VARCHAR(255),
    IN p_obs TEXT,
    IN p_status VARCHAR(50)
)
BEGIN
 
    IF p_quantidade IS NULL OR p_quantidade <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade inválida (deve ser > 0)';
    END IF;

    IF p_origem_local IS NULL OR p_origem_local = '' OR p_destino_local IS NULL OR p_destino_local = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Origem e destino devem ser informados';
    END IF;

    IF p_origem_local = p_destino_local THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Origem e destino não podem ser iguais';
    END IF;

 
    IF NOT (
        (p_sku IS NULL AND p_codigo_kit IS NULL) OR
        (p_sku IS NOT NULL AND p_codigo_kit IS NULL) OR
        (p_sku IS NULL AND p_codigo_kit IS NOT NULL)
    ) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Transferência deve referenciar SKU ou KIT ou apenas produto';
    END IF;

   
    IF p_sku IS NOT NULL THEN
        IF NOT EXISTS (SELECT 1 FROM variacao_produto WHERE sku = p_sku) THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'SKU não encontrado';
        END IF;
    END IF;

 
    IF p_codigo_kit IS NOT NULL THEN
        IF NOT EXISTS (SELECT 1 FROM kit WHERE codigo_kit = p_codigo_kit) THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Kit não encontrado';
        END IF;
    END IF;

    
    INSERT INTO movimentacao_multilocalizacao (
        codigo_prod, sku, codigo_kit, origem_local, destino_local,
        quantidade, motivo, doc_referencia, responsavel, obs, status_transferencia, data_hora_transferencia
    ) VALUES (
        p_codigo_prod, p_sku, p_codigo_kit, p_origem_local, p_destino_local,
        p_quantidade, p_motivo, p_doc_referencia, p_responsavel, p_obs, p_status, NOW()
    );

    -- Observação: o schema atual não define saldos por local.
    -- Se houver tabela de saldo por local, a atualização de saldos deve ser implementada separadamente.
END;
//
DELIMITER ;

-- View: vw_transferencias (consulta detalhada)

DROP VIEW IF EXISTS vw_transferencias;
CREATE VIEW vw_transferencias AS
SELECT
    m.id_transferencia,
    m.codigo_prod,
    p.nome_prod,
    m.sku,
    v.tamanho_prod_var,
    v.cor_prod_var,
    v.modelo_prod_var,
    m.codigo_kit,
    k.nome_kit,
    m.origem_local,
    m.destino_local,
    m.quantidade,
    m.motivo,
    m.doc_referencia,
    m.responsavel,
    m.obs,
    m.status_transferencia,
    m.data_hora_transferencia
FROM movimentacao_multilocalizacao m
LEFT JOIN produto p ON m.codigo_prod = p.codigo_prod
LEFT JOIN variacao_produto v ON m.sku = v.sku
LEFT JOIN kit k ON m.codigo_kit = k.codigo_kit;

-- View: vw_transferencias_resumo (soma por local e item)

DROP VIEW IF EXISTS vw_transferencias_resumo;
CREATE VIEW vw_transferencias_resumo AS
SELECT
    COALESCE(m.sku, m.codigo_prod) AS item,
    m.origem_local,
    m.destino_local,
    SUM(m.quantidade) AS total_qtd,
    MIN(m.data_hora_transferencia) AS primeira_transferencia,
    MAX(m.data_hora_transferencia) AS ultima_transferencia
FROM movimentacao_multilocalizacao m
GROUP BY COALESCE(m.sku, m.codigo_prod), m.origem_local, m.destino_local;


-- ============================================================
-- PROCEDURES E VIEWS PARA INVENTARIO
-- ============================================================

DELIMITER //

-- Procedure: criar_inventario
DROP PROCEDURE IF EXISTS criar_inventario;
//
CREATE PROCEDURE criar_inventario(
    IN p_data_inventario DATE,
    IN p_responsavel VARCHAR(255),
    IN p_observacao TEXT
)
BEGIN
    INSERT INTO inventario (data_inventario, responsavel, observacao, status_inventario, data_hora_criacao)
    VALUES (p_data_inventario, p_responsavel, p_observacao, 'Aberto', NOW());
   
    SELECT LAST_INSERT_ID();
END;
//
DELIMITER ;

-- Procedure: registrar_contagem_inventario
DELIMITER //
DROP PROCEDURE IF EXISTS registrar_contagem_inventario;
//
CREATE PROCEDURE registrar_contagem_inventario(
    IN p_id_inventario BIGINT,
    IN p_codigo_prod VARCHAR(100),
    IN p_sku VARCHAR(100),
    IN p_quantidade_contada INT,
    IN p_responsavel VARCHAR(255)
)
BEGIN
    IF p_id_inventario IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Inventário inválido';
    END IF;
    IF p_quantidade_contada IS NULL OR p_quantidade_contada < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade contada inválida';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM inventario WHERE id_inventario = p_id_inventario AND status_inventario = 'Aberto') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Inventário não encontrado ou não está aberto';
    END IF;

    IF EXISTS (SELECT 1 FROM inventario_item WHERE id_inventario = p_id_inventario AND codigo_prod = p_codigo_prod AND ( (p_sku IS NULL AND sku IS NULL) OR (p_sku IS NOT NULL AND sku = p_sku) )) THEN
        UPDATE inventario_item
        SET quantidade_contada = p_quantidade_contada,
            responsavel_contagem = p_responsavel,
            data_hora_contagem = NOW()
        WHERE id_inventario = p_id_inventario AND codigo_prod = p_codigo_prod AND ( (p_sku IS NULL AND sku IS NULL) OR (p_sku IS NOT NULL AND sku = p_sku) );
    ELSE
        INSERT INTO inventario_item (id_inventario, codigo_prod, sku, quantidade_contada, responsavel_contagem, data_hora_contagem)
        VALUES (p_id_inventario, p_codigo_prod, p_sku, p_quantidade_contada, p_responsavel, NOW());
    END IF;
END;
//
DELIMITER ;

-- Procedure: gerar_ajustes_inventario: Insere registros em ajuste_estoque para cada discrepância entre contagem e saldo do sistema
DELIMITER //
DROP PROCEDURE IF EXISTS gerar_ajustes_inventario;
//
CREATE PROCEDURE gerar_ajustes_inventario(
    IN p_id_inventario BIGINT,
    IN p_responsavel VARCHAR(255),
    IN p_motivo_padrao VARCHAR(255)
)
BEGIN
    DECLARE v_saldo INT;
    DECLARE v_qtd_contada INT;
    DECLARE v_sku VARCHAR(100);
    DECLARE v_codigo_prod VARCHAR(100);
    DECLARE done INT DEFAULT 0;

    DECLARE cur CURSOR FOR
        SELECT ii.codigo_prod, ii.sku, ii.quantidade_contada
        FROM inventario_item ii
        WHERE ii.id_inventario = p_id_inventario;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_codigo_prod, v_sku, v_qtd_contada;
        IF done = 1 THEN
            LEAVE read_loop;
        END IF;

        IF v_sku IS NOT NULL THEN
            SELECT quantidade_prod INTO v_saldo FROM variacao_produto WHERE sku = v_sku;
        ELSE
            SELECT COALESCE(SUM(quantidade_prod),0) INTO v_saldo FROM variacao_produto WHERE codigo_prod = v_codigo_prod;
        END IF;

        IF v_saldo IS NULL THEN
            SET v_saldo = 0;
        END IF;

        IF v_saldo <> v_qtd_contada THEN
            INSERT INTO ajuste_estoque (
                codigo_prod, sku, quantidade_anterior, quantidade_posterior, quantidade_ajuste,
                motivo_ajuste, responsavel_ajuste, data_hora_ajuste
            ) VALUES (
                v_codigo_prod, v_sku, v_saldo, v_qtd_contada, (v_qtd_contada - v_saldo),
                p_motivo_padrao, p_responsavel, NOW()
            );
        END IF;
    END LOOP;
    CLOSE cur;
END;
//
DELIMITER ;

-- ============================================================
-- Views para Inventário
-- ============================================================

DROP VIEW IF EXISTS vw_inventario_resumo;
CREATE VIEW vw_inventario_resumo AS
SELECT
    i.id_inventario,
    i.data_inventario,
    i.responsavel,
    i.status_inventario,
    i.observacao,
    COUNT(ii.id_inventario_item) AS total_itens,
    MIN(ii.data_hora_contagem) AS primeira_contagem,
    MAX(ii.data_hora_contagem) AS ultima_contagem
FROM inventario i
LEFT JOIN inventario_item ii ON i.id_inventario = ii.id_inventario
GROUP BY i.id_inventario, i.data_inventario, i.responsavel, i.status_inventario, i.observacao;

DROP VIEW IF EXISTS vw_inventario_discrepancias;
CREATE VIEW vw_inventario_discrepancias AS
SELECT
    ii.id_inventario,
    ii.codigo_prod,
    ii.sku,
    COALESCE(v.quantidade_prod, 0) AS quantidade_sistema,
    ii.quantidade_contada,
    (ii.quantidade_contada - COALESCE(v.quantidade_prod, 0)) AS diferenca
FROM inventario_item ii
LEFT JOIN variacao_produto v ON ii.sku = v.sku
LEFT JOIN produto p ON ii.codigo_prod = p.codigo_prod;

-- ============================================================
-- PROCEDURES E VIEWS PARA AJUSTE DE ESTOQUE
-- ============================================================

DELIMITER //

-- Procedure: registrar_ajuste_estoque
DROP PROCEDURE IF EXISTS registrar_ajuste_estoque;
//
CREATE PROCEDURE registrar_ajuste_estoque(
    IN p_codigo_prod VARCHAR(100),
    IN p_sku VARCHAR(100),
    IN p_quantidade_anterior INT,
    IN p_quantidade_posterior INT,
    IN p_quantidade_ajuste INT,
    IN p_motivo_ajuste VARCHAR(255),
    IN p_responsavel_ajuste VARCHAR(255)
)
BEGIN
    IF p_codigo_prod IS NULL OR p_codigo_prod = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Produto obrigatório';
    END IF;

    IF p_quantidade_ajuste = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade de ajuste deve ser diferente de zero';
    END IF;

    IF p_sku IS NOT NULL THEN
        IF NOT EXISTS (SELECT 1 FROM variacao_produto WHERE sku = p_sku) THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'SKU não encontrado';
        END IF;
    END IF;

    INSERT INTO ajuste_estoque (
        codigo_prod, sku, quantidade_anterior, quantidade_posterior, quantidade_ajuste,
        motivo_ajuste, responsavel_ajuste, data_hora_ajuste
    ) VALUES (
        p_codigo_prod, p_sku, p_quantidade_anterior, p_quantidade_posterior, p_quantidade_ajuste,
        p_motivo_ajuste, p_responsavel_ajuste, NOW()
    );

    SELECT LAST_INSERT_ID() AS id_ajuste;
END;
//
DELIMITER ;

-- Procedure: aplicar_ajuste_estoque: Atualiza saldo (variacao_produto.quantidade_prod ou kit.quantidade_kit) e insere movimentacao_estoque
DELIMITER //
DROP PROCEDURE IF EXISTS aplicar_ajuste_estoque;
//
CREATE PROCEDURE aplicar_ajuste_estoque(
    IN p_id_ajuste BIGINT,
    IN p_responsavel_execucao VARCHAR(255)
)
BEGIN
    DECLARE v_codigo_prod VARCHAR(100);
    DECLARE v_sku VARCHAR(100);
    DECLARE v_qtd_anterior INT;
    DECLARE v_qtd_posterior INT;
    DECLARE v_qtd_ajuste INT;
    DECLARE v_tipo_mov VARCHAR(20);
    DECLARE v_saldo_atual INT;

    SELECT codigo_prod, sku, quantidade_anterior, quantidade_posterior, quantidade_ajuste
    INTO v_codigo_prod, v_sku, v_qtd_anterior, v_qtd_posterior, v_qtd_ajuste
    FROM ajuste_estoque
    WHERE id_ajuste = p_id_ajuste
    FOR UPDATE;

    IF v_codigo_prod IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ajuste não encontrado';
    END IF;

    IF v_qtd_ajuste > 0 THEN
        SET v_tipo_mov = 'Entrada';
    ELSE
        SET v_tipo_mov = 'Saida';
    END IF;

    IF v_sku IS NOT NULL THEN
        SELECT quantidade_prod INTO v_saldo_atual FROM variacao_produto WHERE sku = v_sku FOR UPDATE;
        IF v_saldo_atual IS NULL THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'SKU não encontrado ao aplicar ajuste';
        END IF;

        IF v_tipo_mov = 'Saida' AND v_saldo_atual < ABS(v_qtd_ajuste) THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente para aplicar ajuste';
        END IF;

        UPDATE variacao_produto
        SET quantidade_prod = quantidade_prod + v_qtd_ajuste
        WHERE sku = v_sku;

        INSERT INTO movimentacao_estoque (
            codigo_prod, sku, codigo_kit, tipo_mov_es, quantidade_mov_es,
            motivo_mov_es, doc_mov_es, origem_mov_es, destino_mov_es,
            responsavel_mov_es, obs_mov_es, data_hora_movimentacao
        ) VALUES (
            v_codigo_prod, v_sku, NULL, v_tipo_mov, ABS(v_qtd_ajuste),
            CONCAT('Ajuste id=', p_id_ajuste), NULL, 'Ajuste', NULL,
            p_responsavel_execucao, NULL, NOW()
        );
    ELSE
        IF EXISTS (SELECT 1 FROM kit WHERE codigo_kit = v_codigo_prod) THEN
            SELECT quantidade_kit INTO v_saldo_atual FROM kit WHERE codigo_kit = v_codigo_prod FOR UPDATE;
            IF v_tipo_mov = 'Saida' AND v_saldo_atual < ABS(v_qtd_ajuste) THEN
                SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente para aplicar ajuste no kit';
            END IF;

            UPDATE kit
            SET quantidade_kit = quantidade_kit + v_qtd_ajuste
            WHERE codigo_kit = v_codigo_prod;

            INSERT INTO movimentacao_estoque (
                codigo_prod, sku, codigo_kit, tipo_mov_es, quantidade_mov_es,
                motivo_mov_es, doc_mov_es, origem_mov_es, destino_mov_es,
                responsavel_mov_es, obs_mov_es, data_hora_movimentacao
            ) VALUES (
                v_codigo_prod, NULL, v_codigo_prod, v_tipo_mov, ABS(v_qtd_ajuste),
                CONCAT('Ajuste id=', p_id_ajuste), NULL, 'Ajuste', NULL,
                p_responsavel_execucao, NULL, NOW()
            );
        ELSE
           
            INSERT INTO movimentacao_estoque (
                codigo_prod, sku, codigo_kit, tipo_mov_es, quantidade_mov_es,
                motivo_mov_es, doc_mov_es, origem_mov_es, destino_mov_es,
                responsavel_mov_es, obs_mov_es, data_hora_movimentacao
            ) VALUES (
                v_codigo_prod, NULL, NULL, v_tipo_mov, ABS(v_qtd_ajuste),
                CONCAT('Ajuste id=', p_id_ajuste), NULL, 'Ajuste', NULL,
                p_responsavel_execucao, NULL, NOW()
            );
        END IF;
    END IF;

    UPDATE ajuste_estoque
    SET data_hora_execucao = NOW(), responsavel_execucao = p_responsavel_execucao
    WHERE id_ajuste = p_id_ajuste;
END;
//
DELIMITER ;

-- ============================================================
-- View: vw_ajustes_estoque (consulta)
-- ============================================================
DROP VIEW IF EXISTS vw_ajustes_estoque;
CREATE VIEW vw_ajustes_estoque AS
SELECT
    a.id_ajuste,
    a.codigo_prod,
    a.sku,
    a.quantidade_anterior,
    a.quantidade_posterior,
    a.quantidade_ajuste,
    a.motivo_ajuste,
    a.responsavel_ajuste,
    a.data_hora_ajuste,
    a.responsavel_execucao,
    a.data_hora_execucao
FROM ajuste_estoque a;


-- ============================================================
-- RECEBIMENTO E CONFERENCIA: PROCEDURE, VIEWS E TRIGGERS
-- ============================================================

DELIMITER //

-- Procedure: registrar_recebimento_conferencia
DROP PROCEDURE IF EXISTS registrar_recebimento_conferencia;
//
CREATE PROCEDURE registrar_recebimento_conferencia(
    IN p_id_pedido_compra BIGINT,
    IN p_codigo_prod VARCHAR(100),
    IN p_sku VARCHAR(100),
    IN p_quantidade_recebida INT,
    IN p_lote VARCHAR(100),
    IN p_numero_serie VARCHAR(100),
    IN p_responsavel VARCHAR(255),
    IN p_obs TEXT
)
BEGIN
    DECLARE v_pedido_exist INT;
    DECLARE v_item_exist INT;
    DECLARE v_qtd_pendente INT;
    DECLARE v_id_recebimento BIGINT;

    IF p_id_pedido_compra IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Pedido de compra obrigatório';
    END IF;
    IF p_quantidade_recebida IS NULL OR p_quantidade_recebida <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade recebida inválida';
    END IF;

    SELECT COUNT(1) INTO v_pedido_exist FROM pedido_compra WHERE id_pedido = p_id_pedido_compra;
    IF v_pedido_exist = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Pedido de compra não encontrado';
    END IF;

    SELECT COUNT(1) INTO v_item_exist FROM pedido_compra_item
    WHERE id_pedido_compra = p_id_pedido_compra AND codigo_prod = p_codigo_prod
      AND ( (p_sku IS NULL AND sku IS NULL) OR (p_sku IS NOT NULL AND sku = p_sku) );
    IF v_item_exist = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Item não encontrado no pedido';
    END IF;

    INSERT INTO recebimento_conferencia (
        id_pedido_compra, codigo_prod, sku, quantidade_recebida, lote, numero_serie, responsavel, obs, data_hora_recebimento
    ) VALUES (
        p_id_pedido_compra, p_codigo_prod, p_sku, p_quantidade_recebida, p_lote, p_numero_serie, p_responsavel, p_obs, NOW()
    );

    SET v_id_recebimento = LAST_INSERT_ID();

    IF p_lote IS NOT NULL AND p_lote <> '' THEN
        IF EXISTS (SELECT 1 FROM lote_serie WHERE codigo_prod = p_codigo_prod AND lote = p_lote) THEN
            UPDATE lote_serie
            SET numero_serie = COALESCE(p_numero_serie, numero_serie)
            WHERE codigo_prod = p_codigo_prod AND lote = p_lote;
        ELSE
            INSERT INTO lote_serie (codigo_prod, lote, numero_serie, fornecedor)
            VALUES (p_codigo_prod, p_lote, p_numero_serie, NULL);
        END IF;
    END IF;

    IF p_numero_serie IS NOT NULL AND p_numero_serie <> '' THEN
        IF NOT EXISTS (SELECT 1 FROM lote_serie WHERE codigo_prod = p_codigo_prod AND numero_serie = p_numero_serie) THEN
            INSERT INTO lote_serie (codigo_prod, lote, numero_serie, fornecedor)
            VALUES (p_codigo_prod, p_lote, p_numero_serie, NULL);
        END IF;
    END IF;

    CALL registrar_movimentacao_estoque(p_codigo_prod, p_sku, NULL, 'Entrada', p_quantidade_recebida, 'Recebimento id=' || v_id_recebimento, NULL, 'Recebimento', NULL, p_responsavel, p_obs);

    UPDATE pedido_compra_item
    SET quantidade_recebida = COALESCE(quantidade_recebida,0) + p_quantidade_recebida
    WHERE id_pedido_compra = p_id_pedido_compra AND codigo_prod = p_codigo_prod
      AND ( (p_sku IS NULL AND sku IS NULL) OR (p_sku IS NOT NULL AND sku = p_sku) );

    IF NOT EXISTS (
        SELECT 1 FROM pedido_compra_item
        WHERE id_pedido_compra = p_id_pedido_compra AND (quantidade_pedido - COALESCE(quantidade_recebida,0)) > 0
    ) THEN
        UPDATE pedido_compra SET status_pedido = 'Recebido', data_recebimento = NOW() WHERE id_pedido = p_id_pedido_compra;
    END IF;

    SELECT v_id_recebimento;
END;
//
DELIMITER ;

-- ============================================================
-- View: vw_recebimentos (consulta para telas)
-- ============================================================
DROP VIEW IF EXISTS vw_recebimentos;
CREATE VIEW vw_recebimentos AS
SELECT
    r.id_recebimento,
    r.id_pedido_compra,
    r.codigo_prod,
    p.nome_prod,
    r.sku,
    v.tamanho_prod_var,
    v.cor_prod_var,
    r.quantidade_recebida,
    r.lote,
    r.numero_serie,
    r.responsavel,
    r.obs,
    r.data_hora_recebimento
FROM recebimento_conferencia r
LEFT JOIN produto p ON r.codigo_prod = p.codigo_prod
LEFT JOIN variacao_produto v ON r.sku = v.sku;


-- Observacoes importantes:
-- 1. A documentação não define uma tabela própria de fornecedor, localização ou usuário. por isso, esses dados foram mantidos como texto onde aparecem no Model/documento.
-- 2. O documento descreve pedido de compra e recebimento, mas o Model fornecido não contem campos suficientes para estruturar essas entidades sem acrescentar dados.
-- 3. Reserva/ATP nao foi modelada como tabela porque a politica e os campos necessarios ainda dependem de definicao com Vendas.
-- 4. O saldo geral de produto versus saldo por SKU e a montagem/desmontagem de kits ainda precisam de regra funcional para evitar dupla contagem.
-- 5. Como o Model de Estoque será refeito, provavelmente muitas coisas da base de dados poderão ser atualizadas até a data estipulada para a alteração final do model de estoque (09/10/2026)
