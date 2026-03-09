package br.com.forumhub.ForumHub.service;

import br.com.forumhub.ForumHub.dto.CursoDto;
import br.com.forumhub.ForumHub.model.Curso;
import br.com.forumhub.ForumHub.model.Usuario;
import br.com.forumhub.ForumHub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public void AdicionarCurso(CursoDto dados) {
        Usuario usuario = getUsuarioLogado();
        var dadosCurso = cursoRepository.nomeCurso();
        boolean cursoExistente = false;

        for (int i = 0; i < dadosCurso.size(); i++) {
            var dadosCursoIndice = dadosCurso.get(i);
            var cursoExiste = dadosCursoIndice.equals(dados.nome());

            if (cursoExiste) {
                cursoExistente = true;
                i = dadosCurso.size() + 1;
            }
        }

        boolean admin = isAdmin(usuario);

        if (!admin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para adicionar cursos");
        }

        if (cursoExistente) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Curso já cadastrado");
        }
        Curso curso = new Curso(dados);
        cursoRepository.save(curso);
    }

    private boolean isAdmin(Usuario usuario) {
        return usuario.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
