package br.com.forumhub.ForumHub.controller;

import br.com.forumhub.ForumHub.dto.ListagemTopicos;
import br.com.forumhub.ForumHub.dto.TopicoAtualizado;
import br.com.forumhub.ForumHub.dto.TopicoDto;
import br.com.forumhub.ForumHub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity AdicionarTopico(@RequestBody @Valid TopicoDto dados) {
        topicoService.AdicionarTopico(dados);
        return ResponseEntity.ok(dados);
    }

    @DeleteMapping ("/{id}")
    @Transactional
    public ResponseEntity DeletarTopico (@PathVariable Long id) {
        topicoService.deletarTopico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ListagemTopicos>> ListarTopicos(@PageableDefault(size = 8, sort = {"dataCriacao"}) Pageable pagina) {
        var page = topicoService.ListarTopicos(pagina);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity ListarUmTopico(@PathVariable Long id) {
        var topico = topicoService.ListarUmTopico(id);
        return ResponseEntity.ok(new ListagemTopicos(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity AtualizarTopico (@RequestBody @Valid TopicoAtualizado topicoAtualizado) {
        var topico = topicoService.AlterarTopico(topicoAtualizado);
        return ResponseEntity.ok(new ListagemTopicos(topico));
    }

}
