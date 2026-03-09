package br.com.forumhub.ForumHub.repository;

import br.com.forumhub.ForumHub.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("SELECT c.nome FROM Curso c")
    List<String> nomeCurso();
}
