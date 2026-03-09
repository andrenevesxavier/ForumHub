package br.com.forumhub.ForumHub.controller;

import br.com.forumhub.ForumHub.dto.CursoDto;
import br.com.forumhub.ForumHub.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Transactional
    public ResponseEntity AdicionarCurso(@RequestBody @Valid CursoDto dados) {
        cursoService.AdicionarCurso(dados);
        return ResponseEntity.ok(dados);
    }
}
