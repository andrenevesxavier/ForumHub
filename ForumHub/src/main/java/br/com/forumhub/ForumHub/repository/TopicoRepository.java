package br.com.forumhub.ForumHub.repository;


import br.com.forumhub.ForumHub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("Select r.titulo from Topico r")
    List<String> DadosTopicoTitulo();

    @Query("SELECT r.mensagem FROM Topico r")
    List<String> DadosTopicoMensagme();
}
