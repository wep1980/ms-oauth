package br.com.wepdev.oauth.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe que copia da Tabela de usuario do micro serviçp usuario.
 *
 * UserDetails -> implementação do spring security para configuração de segurança
 */
public class Usuario implements UserDetails ,Serializable {


    private Long id;

    private String nome;

    private String email;

    private String senha;


    private Set<Role> roles = new HashSet<>();


    public Usuario() {
    }


    public Usuario(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Role> getRoles() {
        return roles;
    }


   // ------------------------ Metodos do UserDetails ------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // Convertendo cada elemento do tipo Role para o tipo GrantedAuthority
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getRoleNome())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Como não vai existir uma regra para esse metodo, ele vai retorna true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Como não vai existir uma regra para esse metodo, ele vai retorna true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Como não vai existir uma regra para esse metodo, ele vai retorna true
    }

    @Override
    public boolean isEnabled() {
        return true; // Como não vai existir uma regra para esse metodo, ele vai retorna true
    }


    // ------------------------  Fim dos Metodos do UserDetails ----------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
