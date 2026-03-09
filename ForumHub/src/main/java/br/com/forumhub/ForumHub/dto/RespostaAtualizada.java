package br.com.forumhub.ForumHub.dto;

import br.com.forumhub.ForumHub.model.Solucao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaAtualizada(@NotNull
                                 Long id,
                                 @NotBlank
                                 String mensagem,
                                 @NotNull
                                 Solucao solucao) {
}
