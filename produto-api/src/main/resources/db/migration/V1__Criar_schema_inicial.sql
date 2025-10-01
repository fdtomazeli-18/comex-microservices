-- V1__Criar_schema_inicial.sql
-- Criação do schema inicial do sistema Comex

CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE produto (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    descricao VARCHAR(1000),
    quantidade_estoque INTEGER NOT NULL,
    categoria_id BIGINT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (categoria_id) REFERENCES categoria (id)
);

CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(14),
    nome VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(20),
    logradouro VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(50),
    uf VARCHAR(2),
    cep VARCHAR(9),
    ativo BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Dados iniciais
INSERT INTO categoria (nome, ativo, created_at, updated_at) VALUES
('Eletrônicos', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Móveis', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Eletrodomésticos', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO produto (nome, preco, descricao, quantidade_estoque, categoria_id, ativo, created_at, updated_at) VALUES
('Smartphone', 1500.00, 'Smartphone com 4GB de RAM e 128GB de armazenamento', 10, 1, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sofá', 1200.00, 'Sofá de 3 lugares com tecido impermeável', 5, 2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Geladeira', 2500.00, 'Geladeira frost-free com 380 litros de capacidade', 3, 3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO cliente (cpf, nome, email, telefone, logradouro, bairro, cidade, uf, cep, ativo, created_at, updated_at) VALUES
('123.456.789-00', 'João Silva', 'joao.silva@example.com', '(11) 98765-4321', 'Rua A', 'Bairro B', 'Cidade C', 'SP', '12345-678', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('987.654.321-00', 'Maria Oliveira', 'maria.oliveira@example.com', '(21) 87654-3210', 'Avenida X', 'Bairro Y', 'Cidade Z', 'RJ', '87654-321', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('456.789.123-00', 'Carlos Souza', 'carlos.souza@example.com', '(31) 76543-2109', 'Travessa Q', 'Bairro W', 'Cidade V', 'MG', '65432-109', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);