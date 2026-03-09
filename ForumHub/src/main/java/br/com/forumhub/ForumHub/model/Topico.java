package br.com.forumhub.ForumHub.model;

import br.com.forumhub.ForumHub.dto.TopicoAtualizado;
import br.com.forumhub.ForumHub.dto.TopicoDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;


    private Boolean status;

    @ManyToOne
    private Usuario autor;

    @ManyToOne
    private Curso curso;

    @OneToMany (mappedBy = "topico", fetch = FetchType.LAZY)
    private List<Resposta> respostas;

    public Topico(@Valid TopicoDto dados, Curso curso, Usuario autor) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = autor;
        this.curso = curso;
        this.status = true;
    }

    public void AutualizaTopico (@Valid TopicoAtualizado dados) {
        if (dados.mensagem() != null && dados.mensagem() != getMensagem()) {
            this.mensagem = dados.mensagem();
        }
        if (dados.titulo() != null && dados.titulo() != getTitulo()) {
            this.titulo = dados.titulo();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
    }

}
