package br.com.forumhub.ForumHub.service.validation.usuariovalidation;

import br.com.forumhub.ForumHub.exception.ValidacaoExeption;
import br.com.forumhub.ForumHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioIdExistente implements UsuarioValidador {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void validar(Long id) {
        var topico = usuarioRepository.existsById(id);

        if (!topico) {
            throw  new ValidacaoExeption("Usuário inexistente");
        }

    }
}
