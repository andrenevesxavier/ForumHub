# ForumHub 🎓

![Java](https://img.shields.io/badge/Java_25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![API REST](https://img.shields.io/badge/API_REST-005571?style=for-the-badge&logo=fastapi&logoColor=white)

Uma plataforma de ensino completa com API REST desenvolvida em Java com Spring Boot, permitindo que alunos, moderadores e administradores interajam através de tópicos, respostas e cursos.

---

## 📋 Descrição do Projeto

O ForumHub é uma API REST de uma plataforma de ensino que permite o gerenciamento de usuários, cursos, tópicos e respostas. O sistema conta com autenticação via JWT e controle de acesso baseado em perfis (RBAC), garantindo que cada tipo de usuário tenha permissões adequadas dentro da plataforma.

---

## 🚀 Tecnologias Utilizadas

- **Java 25**
- **Spring Boot 3**
- **Spring Security** — autenticação e autorização
- **Spring Data JPA** — persistência de dados
- **Flyway** — versionamento e migrations do banco de dados
- **PostgreSQL** — banco de dados relacional
- **JWT (JSON Web Token)** — geração e validação de tokens
- **Lombok** — redução de boilerplate
- **Maven** — gerenciamento de dependências
- **BCrypt** — criptografia de senhas

---

## 🗄️ Modelo do Banco de Dados

```
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│   usuarios  │       │   topicos   │       │   respostas │
│─────────────│       │─────────────│       │─────────────│
│ id          │──┐    │ id          │──┐    │ id          │
│ nome        │  │    │ titulo      │  │    │ mensagem    │
│ email       │  │    │ mensagem    │  │    │ data_criacao│
│ senha       │  └───>│ autor_id    │  └───>│ topico_id   │
│ ativo       │  ┌───>│ curso_id    │       │ autor_id    │
└──────┬──────┘  │    │ data_criacao│       │ solucao     │
       │         │    │ status      │       └─────────────┘
       │         │    └─────────────┘
       │    ┌────┘
       │    │    ┌─────────────┐
       │    └────│   cursos    │
       │         │─────────────│
       │         │ id          │
       │         │ nome        │
       │         │ categoria   │
       │         └─────────────┘
       │
       │    ┌──────────────────┐     ┌─────────────┐
       └────│ usuario_perfis   │─────│   perfis    │
            │──────────────────│     │─────────────│
            │ usuario_id       │     │ id          │
            │ perfil_id        │     │ nome        │
            └──────────────────┘     └─────────────┘
```

### Perfis disponíveis

| ID | Perfil           | Descrição                          |
|----|------------------|------------------------------------|
| 1  | ROLE_ADMIN       | Acesso total ao sistema            |
| 2  | ROLE_MODERADOR   | Gerencia tópicos e respostas       |
| 3  | ROLE_ALUNO       | Cria tópicos e respostas           |

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL

### Configuração do Banco de Dados

Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE forumhub;
```

### Configuração do `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/forumhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=none

api.security.token.secret=seu_secret_jwt
```

### Rodando o Projeto

```bash
# Clone o repositório
git clone https://github.com/andrenevesxavier/Forumhub.git

# Entre na pasta do projeto
cd forumhub

# Rode o projeto
./mvnw spring-boot:run
```

O Flyway irá executar as migrations automaticamente e criar todas as tabelas, perfis e o usuário admin no banco.

### Credenciais do Admin padrão

```
email: admin@forumhub.com
senha: 332200
```

---

## 📡 Documentação dos Endpoints

### 🔓 Autenticação

| Método | Endpoint | Descrição                        | Autenticação |
|--------|----------|----------------------------------|--------------|
| POST   | /login   | Realizar login e obter token JWT | ❌ Livre     |

> Deve haver um usuário cadastrado previamente no banco para realizar o login.

**Body:**
```json
{
    "email": "usuario@email.com",
    "senha": "senha123"
}
```

**Resposta:**
```json
{
    "token": "eyJhbGci..."
}
```

---

### 👤 Usuários — `/usuarios`

| Método | Endpoint                         | Descrição             | Acesso                              |
|--------|----------------------------------|-----------------------|-------------------------------------|
| POST   | /usuarios/adicionar              | Cadastrar usuário     | ❌ Livre                            |
| DELETE | /usuarios/{id}                   | Desativar usuário     | ADMIN (qualquer) / ALUNO (si mesmo) |
| PUT    | /usuarios/{id}/perfis/{perfilId} | Atribuir perfil       | ADMIN                               |
| PUT    | /usuarios/{id}                   | Reativar usuário      | ADMIN                               |

> O perfil `ROLE_ALUNO` é atribuído automaticamente no cadastro.

**Body (cadastro):**
```json
{
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "senha": "senha123"
}
```

---

### 📚 Cursos — `/cursos`

| Método | Endpoint | Descrição       | Acesso |
|--------|----------|-----------------|--------|
| POST   | /cursos  | Cadastrar curso | ADMIN  |

**Body:**
```json
{
    "nome": "Spring Boot",
    "categoria": "Back-End"
}
```

---

### 💬 Tópicos — `/topicos`

| Método | Endpoint      | Descrição        | Acesso                      |
|--------|---------------|------------------|-----------------------------|
| POST   | /topicos      | Criar tópico     | Qualquer perfil autenticado |
| GET    | /topicos      | Listar tópicos   | Qualquer perfil autenticado |
| GET    | /topicos/{id} | Buscar um tópico | Qualquer perfil autenticado |
| PUT    | /topicos      | Atualizar tópico | Dono do tópico              |
| DELETE | /topicos/{id} | Deletar tópico   | Dono, MODERADOR ou ADMIN    |

> A listagem de tópicos é paginada com 8 itens por página, ordenados por data de criação.

**Body (criação):**
```json
{
    "titulo": "Dúvida sobre Spring Security",
    "mensagem": "Como configurar autenticação JWT?",
    "cursoId": 1
}
```

**Body (atualização):**
```json
{
    "id": 1,
    "titulo": "Dúvida sobre Spring Security atualizada",
    "mensagem": "Como configurar o JWT com refresh token?",
    "status": true
}
```

---

### 💭 Respostas — `/resposta`

| Método | Endpoint       | Descrição          | Acesso                                          |
|--------|----------------|--------------------|-------------------------------------------------|
| POST   | /resposta      | Criar resposta     | Qualquer perfil autenticado                     |
| PUT    | /resposta      | Atualizar resposta | Dono da resposta                                |
| DELETE | /resposta/{id} | Deletar resposta   | Dono, MODERADOR ou ADMIN                        |

**Body (criação):**
```json
{
    "mensagem": "Para configurar o JWT adicione a dependência no pom.xml",
    "topico": 1,
    "solucao": "NAO_SOLUCIONADO"
}
```

> Os valores aceitos no campo `solucao` são: `SOLUCIONADO` ou `NAO_SOLUCIONADO`

---

## 🔐 Autenticação

Todos os endpoints protegidos exigem o token JWT no Header da requisição:

```
Authorization: Bearer eyJhbGci...
```

O token é obtido realizando login no endpoint `POST /login`.

---

## 👥 Controle de Acesso

| Ação                             | ALUNO | MODERADOR | ADMIN |
|----------------------------------|-------|-----------|-------|
| Criar tópico/resposta            | ✔     | ✔         | ✔     |
| Listar tópicos                   | ✔     | ✔         | ✔     |
| Atualizar próprio tópico         | ✔     | ✔         | ✔     |
| Atualizar própria resposta       | ✔     | ✔         | ✔     |
| Deletar próprio tópico/resposta  | ✔     | ✔         | ✔     |
| Deletar tópico/resposta de outro | ❌    | ✔         | ✔     |
| Deletar próprio usuário          | ✔     | ❌        | ✔     |
| Deletar qualquer usuário         | ❌    | ❌        | ✔     |
| Reativar usuário                 | ❌    | ❌        | ✔     |
| Atribuir perfis                  | ❌    | ❌        | ✔     |
| Cadastrar cursos                 | ❌    | ❌        | ✔     |

---

## 👨‍💻 Autor

Desenvolvido por **André Neves Xavier**

<a href="https://linkedin.com/in/andre-neves-xavier" target="_blank">
  <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" />
</a>
<a href="https://github.com/andrenevesxavier" target="_blank">
  <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" />
</a>

Desenvolvido como projeto de estudo de Spring Boot e Spring Security.
