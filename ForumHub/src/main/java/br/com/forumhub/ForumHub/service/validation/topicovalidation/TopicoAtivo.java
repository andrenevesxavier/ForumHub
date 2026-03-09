package br.com.forumhub.ForumHub.service.validation.topicovalidation;

import br.com.forumhub.ForumHub.exception.ValidacaoExeption;
import br.com.forumhub.ForumHub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoAtivo implements  TopicoValidador{

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(Long id) {

        var topico = topicoRepository.getReferenceById(id);
        var statusTopico = topico.getStatus().equals(true);

        if (!statusTopico) {
           throw new ValidacaoExeption("Esse topico já foi resolvido");
        }

    }
}
