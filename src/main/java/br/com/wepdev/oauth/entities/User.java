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
public class User implements UserDetails ,Serializable {


    private Long id;

    private String name;

    private String email;

    private String password;


    private Set<Role> roles = new HashSet<>();


    public User() {
    }


    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }


   // ------------------------ Metodos do UserDetails ------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // Convertendo cada elemento do tipo Role para o tipo GrantedAuthority
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getRoleName())).collect(Collectors.toList());
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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
