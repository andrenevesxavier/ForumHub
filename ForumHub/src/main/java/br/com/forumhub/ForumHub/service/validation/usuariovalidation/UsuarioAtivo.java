package br.com.forumhub.ForumHub.service.validation.usuariovalidation;

import br.com.forumhub.ForumHub.exception.ValidacaoExeption;
import br.com.forumhub.ForumHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAtivo implements UsuarioValidador {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(Long id) {

        var usuario = usuarioRepository.getReferenceById(id);
        var statusUsuario = usuario.getAtivo().equals(true);

        if (!statusUsuario) {
           throw  new ValidacaoExeption("Usuario desativado");
        }

    }
}