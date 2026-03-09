package br.com.forumhub.ForumHub.service;

import br.com.forumhub.ForumHub.dto.RespostaAtualizada;
import br.com.forumhub.ForumHub.dto.RespostaDto;
import br.com.forumhub.ForumHub.model.Resposta;
import br.com.forumhub.ForumHub.model.Topico;
import br.com.forumhub.ForumHub.model.Usuario;
import br.com.forumhub.ForumHub.repository.RespostaRepository;
import br.com.forumhub.ForumHub.repository.TopicoRepository;
import br.com.forumhub.ForumHub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    public void AdicionarResposta(RespostaDto dados) {
        Usuario usuario = getUsuarioLogado();
        Topico topico = topicoRepository.getReferenceById(dados.topico());
        Usuario autor = usuarioRepository.getReferenceById(usuario.getId());
        Resposta resposta = new Resposta(dados, topico, autor);
        respostaRepository.save(resposta);
    }

    public void deletarResposta(Long id) {
        Usuario usuarioLogado = getUsuarioLogado();
        Resposta resposta = respostaRepository.getReferenceById(id);

        boolean dono = resposta.getAutor().getId().equals(usuarioLogado.getId());
        boolean moderador = isModerador(usuarioLogado);
        boolean admin = isAdmin(usuarioLogado);

        if (!dono && !moderador && !admin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para deletar essa resposta");
        }
        respostaRepository.delete(resposta);
    }

    public Resposta AlterarResposta(@Valid RespostaAtualizada respostaAtualizada) {
        Usuario usuarioLogado = getUsuarioLogado();
        Resposta resposta = respostaRepository.getReferenceById(respostaAtualizada.id());

        boolean dono = resposta.getAutor().getId().equals(usuarioLogado.getId());

        if (!dono) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para alterar essa resposta");
        }
        resposta.AtualizarResposta(respostaAtualizada);
        return resposta;
    }

    private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private boolean isAdmin(Usuario usuario) {
        return usuario.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private boolean isModerador(Usuario usuario) {
        return usuario.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MODERADOR"));
    }
}
