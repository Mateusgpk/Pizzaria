CREATE DATABASE IF NOT EXISTS pizzaria;
USE pizzaria;

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    telefone VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS TipoSabores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    precocm DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS sabores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    idTipoSabor BIGINT NOT NULL,
    CONSTRAINT fk_sabores_tipo
        FOREIGN KEY (idTipoSabor) REFERENCES TipoSabores(id)
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idCliente BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'Aberto',
    valorTotal DOUBLE NOT NULL DEFAULT 0,
    CONSTRAINT fk_pedidos_cliente
        FOREIGN KEY (idCliente) REFERENCES clientes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS pizza (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cms DOUBLE NOT NULL,
    tipoForma INT NOT NULL,
    idPedido BIGINT NOT NULL,
    dimensao DOUBLE NOT NULL,
    CONSTRAINT fk_pizza_pedido
        FOREIGN KEY (idPedido) REFERENCES pedidos(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS PizzaSabores (
    idPizza BIGINT NOT NULL,
    idSabor BIGINT NOT NULL,
    PRIMARY KEY (idPizza, idSabor),
    CONSTRAINT fk_pizzasabores_pizza
        FOREIGN KEY (idPizza) REFERENCES pizza(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_pizzasabores_sabor
        FOREIGN KEY (idSabor) REFERENCES sabores(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT IGNORE INTO TipoSabores (nome, precocm) VALUES
    ('Simples', 0.05),
    ('Especial', 0.08),
    ('Premium', 0.12);
