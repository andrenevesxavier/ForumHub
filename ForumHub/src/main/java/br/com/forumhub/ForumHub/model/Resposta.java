package br.com.forumhub.ForumHub.model;

import br.com.forumhub.ForumHub.dto.RespostaAtualizada;
import br.com.forumhub.ForumHub.dto.RespostaDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @ManyToOne
    private Topico topico;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    private Usuario autor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Solucao solucao;


    public Resposta(@Valid RespostaDto dados, Topico topico, Usuario autor) {
        this.mensagem = dados.mensagem();
        this.topico = topico;
        this.autor = autor;
        this.solucao = dados.solucao();
    }

    public void AtualizarResposta(@Valid RespostaAtualizada dados) {
        if (dados.id() != null) {
            this.id = dados.id();
        }
        if (dados.mensagem() != null && dados.mensagem() != getMensagem()) {
            this.mensagem = dados.mensagem();
        }
        if (dados.solucao() != null) {
            this.solucao = dados.solucao();
        }
    }
}
