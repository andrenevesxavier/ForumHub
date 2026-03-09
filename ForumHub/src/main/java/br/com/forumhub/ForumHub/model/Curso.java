package br.com.forumhub.ForumHub.model;

import br.com.forumhub.ForumHub.dto.CursoDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity
@Table (name = "cursos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String categoria;

    public Curso(@Valid CursoDto dados) {
        this.nome= dados.nome();
        this.categoria = dados.categoria();
    }
}
