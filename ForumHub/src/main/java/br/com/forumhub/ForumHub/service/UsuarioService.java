package br.com.forumhub.ForumHub.service;

import br.com.forumhub.ForumHub.dto.UsuarioDto;
import br.com.forumhub.ForumHub.exception.ValidacaoExeption;
import br.com.forumhub.ForumHub.model.Perfil;
import br.com.forumhub.ForumHub.model.Usuario;
import br.com.forumhub.ForumHub.repository.PerfilRepository;
import br.com.forumhub.ForumHub.repository.UsuarioRepository;
import br.com.forumhub.ForumHub.service.validation.usuariovalidation.UsuarioValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;


    @Autowired
    private List<UsuarioValidador> verificarUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void AdicionarUsuario(UsuarioDto dados) {
        Perfil perfil = perfilRepository.getReferenceById(3L);
        Usuario usuario = new Usuario(dados, perfil);
        usuario.setSenha(passwordEncoder.encode(dados.senha()));
        usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {

        verificarUsuario.forEach(v -> v.validar(id));

        Usuario usuarioLogado = getUsuarioLogado();
        Usuario usuario = usuarioRepository.getReferenceById(id);

        boolean dono = usuario.getId().equals(usuarioLogado.getId());
        boolean admin = isAdmin(usuarioLogado);
        boolean moderador = isModerador(usuarioLogado);

        if (moderador) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para deletar usuarios");
        }

        if (!admin && !dono) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para deletar esse usuario");
        }
        usuario.DesativarUsuario();
    }

    public void ReativarUsuario (Long usuario) {
        Usuario usuarioLogado = getUsuarioLogado();
        Usuario usuario1 = usuarioRepository.getReferenceById(usuario);
        boolean admin = isAdmin(usuarioLogado);

        if (admin) {
            usuario1.ReativarUsuario();
        }else {
            throw new ValidacaoExeption("Voce não tem autorização para fazer essa mudança");
        }
    }

    public void mudancaPerfil (Long usuario, Long perfil) {

        verificarUsuario.forEach(v -> v.validar(usuario));

        Usuario usuarioLogado = getUsuarioLogado();

        Usuario usuario1 = usuarioRepository.getReferenceById(usuario);
        Perfil perfil1 = perfilRepository.getReferenceById(perfil);

        boolean admin = isAdmin(usuarioLogado);

        if (admin) {
            if (usuario1.getAtivo() == true) {
                usuario1.getPerfis().add(perfil1);
            }
        }else {
            throw new ValidacaoExeption("Voce não tem autorização para fazer essa mudança");
        }
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
