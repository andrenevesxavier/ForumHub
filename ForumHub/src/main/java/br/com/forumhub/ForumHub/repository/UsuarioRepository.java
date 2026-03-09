package br.com.forumhub.ForumHub.repository;

import br.com.forumhub.ForumHub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

}

