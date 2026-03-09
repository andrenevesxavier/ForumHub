package br.com.forumhub.ForumHub.infra.security;

import br.com.forumhub.ForumHub.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenSecurity  {

    @Value("${api.security.token.secret}")
    private String secret;

    // Método responsável por gerar um token JWT para autenticação do usuário
    public String ObterToken(Usuario usuario) {
        try {
            // Define o algoritmo de criptografia HMAC256 com uma chave secreta
            // IMPORTANTE: Esta chave deve estar em um arquivo de configuração, não hardcoded
            var algorithm = Algorithm.HMAC256(secret);

            // Cria e retorna o token JWT
            return JWT.create()
                    // Define o emissor do token (quem gerou o token)
                    .withIssuer("API med.voll")

                    // Define o "dono" do token (geralmente o identificador único do usuário)
                    // Neste caso, o login do usuário
                    .withSubject(usuario.getEmail())

                    // Define a data/hora de expiração do token
                    // Após essa data, o token não será mais válido
                    .withExpiresAt(dataExpiracao())

                    // Assina o token com o algoritmo definido anteriormente
                    // Isso garante a integridade e autenticidade do token
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            // Captura erros que possam ocorrer durante a criação do token
            // e lança uma exceção de runtime com a mensagem de erro
            throw new RuntimeException("OCORREU UM ERRO" + exception);
        }
    }

    // Método privado que calcula e retorna a data/hora de expiração do token
    private Instant dataExpiracao() {
        // LocalDateTime.now() - obtém a data e hora atual do sistema
        // .plusHours(2) - adiciona n horas à data/hora atual
        // Isso significa que o token será válido por 2 horas a partir do momento da criação

        // .toInstant() - converte o LocalDateTime para Instant (formato padrão UTC)
        // ZoneOffset.of("-03:00") - define o fuso horário como GMT-3 (horário de Brasília)
        return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            // Define o algoritmo HMAC256 com a chave secreta para validar a assinatura
            var algorithm = Algorithm.HMAC256(secret);

            // Cria o verificador, valida o token e extrai o subject
            return JWT.require(algorithm)
                    // Valida se o emissor do token é "API med.voll"
                    .withIssuer("API med.voll")
                    // Constrói o verificador com as configurações definidas
                    .build()
                    // Verifica se o token é válido (assinatura, expiração, emissor)
                    .verify(tokenJWT)
                    // Extrai e retorna o subject (username ou ID do usuário)
                    .getSubject();

        } catch (JWTVerificationException exception){
            // Lança exceção caso o token seja inválido, adulterado ou expirado
            throw new RuntimeException("Ocorreu um erro em relação ao token");
        }
    }
}
