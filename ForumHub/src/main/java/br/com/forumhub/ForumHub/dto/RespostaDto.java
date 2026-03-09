package br.com.forumhub.ForumHub.dto;

import br.com.forumhub.ForumHub.model.Solucao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaDto(

        @NotBlank
        String mensagem,

        @NotNull
        Long topico,

        @NotNull
        Solucao solucao) {
}
