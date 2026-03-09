package br.com.forumhub.ForumHub.dto;

import jakarta.validation.constraints.NotBlank;

public record CursoDto(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Categoria é obrigatória")
        String categoria

) {}
