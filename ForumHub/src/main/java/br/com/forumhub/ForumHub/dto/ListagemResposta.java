package br.com.forumhub.ForumHub.dto;

import br.com.forumhub.ForumHub.model.Resposta;
import br.com.forumhub.ForumHub.model.Solucao;

public record ListagemResposta(String mensagem, Solucao solucao) {

    public ListagemResposta (Resposta resposta) {
        this(resposta.getMensagem(), resposta.getSolucao());
    }
}
