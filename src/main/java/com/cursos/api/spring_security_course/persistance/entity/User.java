package com.cursos.api.spring_security_course.persistance.entity;

import com.cursos.api.spring_security_course.persistance.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * este metodo es usado para listar los permisos y roles del usuario
     * @return una lista de permisos y roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return Collections.emptyList();
        if (role.getPermissions() == null) return Collections.emptyList();
        return Stream.concat(
                role.getPermissions().stream().map(p ->
                        new SimpleGrantedAuthority(p.name())), /* Stream of permissions */
                Stream.of(new SimpleGrantedAuthority("ROLE_" + role.name()) /* Stream of role */)
        ).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
