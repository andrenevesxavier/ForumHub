package br.com.forumhub.ForumHub.repository;

import br.com.forumhub.ForumHub.model.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    @Transactional
    void deleteByTopicoId(Long id);
}

