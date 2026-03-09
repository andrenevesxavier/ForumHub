package br.com.forumhub.ForumHub.service;

import br.com.forumhub.ForumHub.dto.ListagemTopicos;
import br.com.forumhub.ForumHub.dto.TopicoAtualizado;
import br.com.forumhub.ForumHub.dto.TopicoDto;
import br.com.forumhub.ForumHub.model.Curso;
import br.com.forumhub.ForumHub.model.Topico;
import br.com.forumhub.ForumHub.model.Usuario;
import br.com.forumhub.ForumHub.repository.CursoRepository;
import br.com.forumhub.ForumHub.repository.RespostaRepository;
import br.com.forumhub.ForumHub.repository.TopicoRepository;
import br.com.forumhub.ForumHub.service.validation.topicovalidation.TopicoValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private List<TopicoValidador> verificadorTopico;

    @Autowired
    private RespostaRepository respostaRepository;

    public void AdicionarTopico(TopicoDto dados) {
        Usuario autor = getUsuarioLogado();
        Curso curso = cursoRepository.getReferenceById(dados.cursoId());
        var tituloTopico = topicoRepository.DadosTopicoTitulo();
        var mensagemTopico = topicoRepository.DadosTopicoMensagme();

        boolean tituloIgual = false;
        boolean mensagemIgual = false;

        for (int i = 0; i < tituloTopico.size(); i++) {
            var titulo = tituloTopico.get(i);
            var tituloRepetido = titulo.equals(dados.titulo());

            if (tituloRepetido) {
                tituloIgual = true;
                i = tituloTopico.size() + 1;
            }
        }

        for (int i = 0; i < mensagemTopico.size(); i++) {
            var mensagemUnica = mensagemTopico.get(i);
            var mensagemRepetida = mensagemUnica.equals(dados.mensagem());

            if (mensagemRepetida) {
                mensagemIgual = true;
                i = mensagemTopico.size() + 1;
            }
        }

        if (tituloIgual && mensagemIgual) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Topico já cadastrado");
        }

        Topico topico = new Topico(dados, curso, autor);
        System.out.println("Esse é o titulo " + tituloIgual + "esse é a mensagem " + mensagemIgual);
        topicoRepository.save(topico);
    }



    public Page<ListagemTopicos> ListarTopicos(Pageable pageable) {
        var page = topicoRepository.findAll(pageable).map(ListagemTopicos::new);
        return page;
    }

    public void deletarTopico(Long id) {

        verificadorTopico.forEach(v -> v.validar(id));

        Usuario usuarioLogado = getUsuarioLogado();
        Topico topico = topicoRepository.getReferenceById(id);

        boolean dono = topico.getAutor().getId().equals(usuarioLogado.getId());
        boolean admin = isAdmin(usuarioLogado);
        boolean moderador = isModerador(usuarioLogado);

        if (!dono && !admin && !moderador) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para deletar esse tópico");
        }

        respostaRepository.deleteByTopicoId(id);
        topicoRepository.deleteById(topico.getId());
    }

    public Topico AlterarTopico (TopicoAtualizado topicoAtualizado) {
        verificadorTopico.forEach(v -> v.validar(topicoAtualizado.id()));
        Usuario usuarioLogado = getUsuarioLogado();
        Topico topico = topicoRepository.getReferenceById(topicoAtualizado.id());

        boolean dono = topico.getAutor().getId().equals(usuarioLogado.getId());

        if (!dono) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para alterar esse tópico");
        }

        topico.AutualizaTopico(topicoAtualizado);
        return topico;
    }

    public Topico ListarUmTopico(Long id) {
        verificadorTopico.forEach(v -> v.validar(id));
        var dadosTopico = topicoRepository.getReferenceById(id);
        return dadosTopico;
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
