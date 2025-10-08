# Setup Microservices - Comex

## Arquitetura

```
Gateway (8080) → Eureka Server (8761)
                      ↓
        ┌─────────────┴─────────────┐
        ↓                           ↓
  usuario-api (8082)          produto-api (8081)
        ↓                           ↓
        └──────── RabbitMQ ─────────┘
```

## Ordem de Execução

### 1. Infraestrutura
```bash
# Subir PostgreSQL e RabbitMQ
docker-compose up -d
```

### 2. Eureka Server (porta 8761)
```bash
cd eureka-server
mvn spring-boot:run
```
Aguarde até ver: "Started EurekaServerApplication"

### 3. Usuario API (porta 8082)
```bash
cd usuario-api
mvn spring-boot:run
```

### 4. Produto API (porta 8081)
```bash
cd produto-api
mvn spring-boot:run
```

### 5. Gateway (porta 8080)
```bash
cd gateway
mvn spring-boot:run
```

## Testando

### 1. Inicializar usuário admin
```bash
curl -X POST http://localhost:8080/usuario-api/login/init
```

### 2. Autenticar e obter token
```bash
curl -X POST http://localhost:8080/usuario-api/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin","senha":"admin123"}'
```

Resposta:
```json
{"token":"eyJ..."}
```

### 3. Acessar produtos com token
```bash
curl -X GET http://localhost:8080/produto-api/api/produtos \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

## Fluxo de Validação

1. Cliente envia requisição para Gateway (8080)
2. Gateway roteia para produto-api via Eureka
3. produto-api recebe requisição no AuthFilter
4. AuthFilter chama usuario-api (via Eureka) para validar token
5. Se válido, requisição continua; se inválido, retorna 401


- Eureka Dashboard: http://localhost:8761
- Gateway: http://localhost:8080
- Usuario API (direto): http://localhost:8082
- Produto API (direto): http://localhost:8081
- RabbitMQ Management: http://localhost:15672 (guest/guest)
