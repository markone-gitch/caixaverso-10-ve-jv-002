# API de Gerenciamento de Produtos

Projeto desenvolvido em Quarkus para gerenciamento de produtos, com autenticação e autorização via JWT.

## Tecnologias utilizadas

- Java
- Quarkus
- Maven
- Hibernate ORM com Panache
- Banco H2 em memória
- SmallRye JWT
- BCrypt para hash de senha
- REST API com JSON

## Funcionalidades implementadas

- Cadastro de usuários
- Login com geração de token JWT
- Usuários padrão carregados ao iniciar a aplicação
- CRUD de produtos
- Controle de acesso por roles:
  - `ADMIN`
  - `USER`

## Usuários padrão

Ao iniciar a aplicação, dois usuários são criados automaticamente:

| Nome | E-mail | Senha | Role |
|---|---|---|---|
| Admin Sistema | admin@loja.com | admin123 | ADMIN |
| User Padrão | user@loja.com | user123 | USER |

## Como executar o projeto

No terminal, dentro da pasta do projeto:

```bash
./mvnw quarkus:dev
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

## Configuração do banco H2

Exemplo de configuração usada em `application.properties`:

```properties
quarkus.datasource.db-kind=h2
quarkus.datasource.username=sa
quarkus.datasource.password=sa
quarkus.datasource.jdbc.url=jdbc:h2:mem:produtos;DB_CLOSE_DELAY=-1

quarkus.hibernate-orm.database.generation=drop-and-create
quarkus.hibernate-orm.log.sql=true
```

## Autenticação

### Login ADMIN

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@loja.com",
    "senha": "admin123"
  }'
```

Resposta esperada:

```json
{
  "token": "TOKEN_JWT_GERADO",
  "tipo": "Bearer",
  "role": "ADMIN"
}
```

## Cadastro de usuário

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "minhaSenha123",
    "role": "USER"
  }'
```

## Endpoints de produtos

Todos os endpoints de produtos exigem token JWT no header:

```text
Authorization: Bearer TOKEN_AQUI
```

### Listar produtos

Permitido para `ADMIN` e `USER`.

```bash
curl -X GET http://localhost:8080/produtos \
  -H "Authorization: Bearer TOKEN_AQUI"
```

### Buscar produto por ID

Permitido para `ADMIN` e `USER`.

```bash
curl -X GET http://localhost:8080/produtos/1 \
  -H "Authorization: Bearer TOKEN_AQUI"
```

### Cadastrar produto

Permitido apenas para `ADMIN`.

```bash
curl -X POST http://localhost:8080/produtos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN_ADMIN_AQUI" \
  -d '{
    "nome": "Caneta Azul",
    "descricao": "Caneta esferográfica azul",
    "preco": 1.99,
    "estoque": 3
  }'
```

### Atualizar produto

Permitido apenas para `ADMIN`.

```bash
curl -X PUT http://localhost:8080/produtos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN_ADMIN_AQUI" \
  -d '{
    "nome": "Caneta Azul",
    "descricao": "Caneta esferográfica azul atualizada",
    "preco": 2.50,
    "estoque": 10
  }'
```

### Remover produto

Permitido apenas para `ADMIN`.

```bash
curl -X DELETE http://localhost:8080/produtos/1 \
  -H "Authorization: Bearer TOKEN_ADMIN_AQUI"
```

## Regras de autorização

| Endpoint | ADMIN | USER |
|---|---|---|
| `GET /produtos` | Sim | Sim |
| `GET /produtos/{id}` | Sim | Sim |
| `POST /produtos` | Sim | Não |
| `PUT /produtos/{id}` | Sim | Não |
| `DELETE /produtos/{id}` | Sim | Não |

## Possíveis respostas de erro

### Não autenticado

```json
{
  "erro": "Não autenticado"
}
```

### Acesso negado

```json
{
  "erro": "Acesso negado"
}
```

### Produto não encontrado

```json
{
  "erro": "Produto não encontrado"
}
```

### Campos obrigatórios não informados

```json
{
  "erro": "Campos obrigatórios não informados"
}
```

## Observações

- A senha dos usuários é armazenada com hash BCrypt.
- O token JWT possui validade de 1 hora.
- O campo `groups` do JWT é usado para validar as roles `ADMIN` e `USER`.
- Usuários comuns podem consultar produtos, mas não podem cadastrar, alterar ou remover produtos.
