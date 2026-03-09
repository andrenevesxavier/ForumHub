package br.com.forumhub.ForumHub.infra.security;

import br.com.forumhub.ForumHub.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenSecurity tokenSecurity;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extrai o token JWT do cabeçalho da requisição
        var tokenJWT = recuperarToken(request);

        // Se existe um token na requisição
        if (tokenJWT != null) {
            // Valida o token e extrai o subject (login do usuário)
            var subject = tokenSecurity.getSubject(tokenJWT);

            // Busca os dados completos do usuário no banco usando o login
            var usuario = repository.findByEmail(subject);

            // Cria o objeto de autenticação com o usuário e suas permissões
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            // Registra o usuário como autenticado no contexto do Spring Security
            // A partir daqui, o Spring sabe quem é o usuário logado nesta requisição
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        /**
         * FilterChain - Interface que representa a cadeia de filtros de uma requisição HTTP.
         * Ao chamar chain.doFilter(), a requisição é passada para o próximo filtro na cadeia.
         * Permite executar lógica antes e depois do processamento da requisição.
         */
        filterChain.doFilter(request, response);
    }


    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");

        if (authorization != null) {
            return authorization.replace("Bearer ", "").trim();
        }
        return null;
    }
}

