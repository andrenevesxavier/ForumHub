package br.com.forumhub.ForumHub.controller;

import br.com.forumhub.ForumHub.dto.LoginDto;
import br.com.forumhub.ForumHub.dto.TokenJwt;
import br.com.forumhub.ForumHub.model.Usuario;
import br.com.forumhub.ForumHub.infra.security.TokenSecurity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenSecurity tokenSecurity;

    @PostMapping
    public ResponseEntity login(@RequestBody  @Valid LoginDto dados) throws Exception{

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var manager = authenticationManager.authenticate(authenticationToken);
        var usuario = (Usuario) manager.getPrincipal();
        var tokenJwt = tokenSecurity.ObterToken(usuario);
        return ResponseEntity.ok(new TokenJwt(tokenJwt));
    }
}
