package br.com.forumhub.ForumHub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoAtualizado(@NotNull
                               Long id,
                               @NotBlank
                               String titulo,
                               @NotBlank
                               String mensagem,
                               @NotNull
                               Boolean status) {
}
