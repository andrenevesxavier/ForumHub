package br.com.forumhub.ForumHub.controller;

import br.com.forumhub.ForumHub.dto.UsuarioDto;
import br.com.forumhub.ForumHub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/adicionar")
    @Transactional
    public ResponseEntity AdicionarUsuario(@RequestBody @Valid UsuarioDto dados) {
        usuarioService.AdicionarUsuario(dados);
        return ResponseEntity.ok(dados);
    }

    @DeleteMapping ("/{id}")
    @Transactional
    public ResponseEntity deletarUsuario (@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/perfis/{perfilId}")
    @Transactional
    public ResponseEntity atribuirPerfil(@PathVariable Long id,
                                         @PathVariable Long perfilId) {
       usuarioService.mudancaPerfil(id, perfilId);
       return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity ReativarUsuario(@RequestBody @PathVariable Long id) {
        usuarioService.ReativarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
