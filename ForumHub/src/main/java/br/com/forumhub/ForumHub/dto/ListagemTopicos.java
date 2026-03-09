package br.com.forumhub.ForumHub.dto;

import br.com.forumhub.ForumHub.model.Topico;

import java.time.LocalDateTime;

public record ListagemTopicos(String titulo, String mensagem, Boolean status, LocalDateTime data, String autor, String curso) {

    public ListagemTopicos(Topico topico) {
        this (topico.getTitulo(), topico.getMensagem(), topico.getStatus(), topico.getDataCriacao(), topico.getAutor().getNome(), topico.getCurso().getNome());
    }
}
