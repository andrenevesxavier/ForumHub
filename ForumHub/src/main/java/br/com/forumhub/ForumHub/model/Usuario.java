package br.com.forumhub.ForumHub.model;

import br.com.forumhub.ForumHub.dto.UsuarioDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column
    private Boolean ativo;

    // Relacionamento ManyToMany com Perfil.
    // Cria a tabela intermediária "usuario_perfis" com as colunas
    // "usuario_id" (referencia Usuario) e "perfil_id" (referencia Perfil)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_perfis",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private List<Perfil> perfis;

    public Usuario(@Valid UsuarioDto dados, Perfil perfil) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.senha = dados.senha();
        this.perfis = new ArrayList<>();
        this.perfis.add(perfil);
        this.ativo=true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis;
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void DesativarUsuario() {
        this.ativo = false;
    }

    public void ReativarUsuario() {
        this.ativo = true;
    }
}
