package br.com.forumhub.ForumHub.controller;


import br.com.forumhub.ForumHub.dto.ListagemResposta;
import br.com.forumhub.ForumHub.dto.RespostaAtualizada;
import br.com.forumhub.ForumHub.dto.RespostaDto;
import br.com.forumhub.ForumHub.model.*;
import br.com.forumhub.ForumHub.service.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resposta")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    @Transactional
    public ResponseEntity AdicionarResposta(@RequestBody @Valid RespostaDto dados) {
        respostaService.AdicionarResposta(dados);
        return ResponseEntity.ok(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity DeletarResposta(@PathVariable Long id) {
        respostaService.deletarResposta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity AlterarResposta(@RequestBody @Valid RespostaAtualizada respostaAtualizada) {
        var resposta = respostaService.AlterarResposta(respostaAtualizada);
        return ResponseEntity.ok(new ListagemResposta(resposta));
    }
}
