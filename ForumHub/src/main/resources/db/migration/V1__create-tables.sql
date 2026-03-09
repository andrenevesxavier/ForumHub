-- Tabela: perfis
CREATE TABLE perfis (
                        id BIGSERIAL PRIMARY KEY,
                        nome VARCHAR(255) NOT NULL
);

-- Tabela: usuarios
CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL
);

-- Tabela intermediária: usuario_perfis
CREATE TABLE usuario_perfis (
                                usuario_id BIGINT NOT NULL,
                                perfil_id BIGINT NOT NULL,
                                PRIMARY KEY (usuario_id, perfil_id),
                                CONSTRAINT fk_usuario_perfis_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                                CONSTRAINT fk_usuario_perfis_perfil FOREIGN KEY (perfil_id) REFERENCES perfis(id)
);

-- Tabela: cursos
CREATE TABLE cursos (
                        id BIGSERIAL PRIMARY KEY,
                        nome VARCHAR(255) NOT NULL,
                        categoria VARCHAR(255) NOT NULL
);

-- Tabela: topicos
CREATE TABLE topicos (
                         id BIGSERIAL PRIMARY KEY,
                         titulo VARCHAR(255) NOT NULL,
                         mensagem TEXT NOT NULL,
                         data_criacao DATE NOT NULL,
                         status BOOLEAN NOT NULL DEFAULT TRUE,
                         autor_id BIGINT NOT NULL,
                         curso_id BIGINT NOT NULL,
                         CONSTRAINT fk_topicos_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
                         CONSTRAINT fk_topicos_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- Tabela: respostas
CREATE TABLE respostas (
                           id BIGSERIAL PRIMARY KEY,
                           mensagem TEXT NOT NULL,
                           data_criacao DATE NOT NULL,
                           solucao BOOLEAN NOT NULL DEFAULT FALSE,
                           topico_id BIGINT NOT NULL,
                           autor_id BIGINT NOT NULL,
                           CONSTRAINT fk_respostas_topico FOREIGN KEY (topico_id) REFERENCES topicos(id),
                           CONSTRAINT fk_respostas_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);