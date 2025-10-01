-- V2__Criar_tabela_usuario_e_associar_cliente.sql

-- Criação da tabela de usuários
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Adiciona a coluna usuario_id na tabela cliente
ALTER TABLE cliente
ADD COLUMN usuario_id BIGINT;

-- Adiciona a restrição de chave estrangeira
ALTER TABLE cliente
ADD CONSTRAINT fk_cliente_usuario
FOREIGN KEY (usuario_id) REFERENCES usuario(id);