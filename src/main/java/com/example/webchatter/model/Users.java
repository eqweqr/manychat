package com.example.webchatter.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
//@NoArgsConstructor
@ToString(callSuper = true)
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
Integer id;
    @Column(name = "login",unique = true)
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "display_name")
    private String displayName;
    public Users(String name, String pass, String displayName){
        this.login=name;
        this.password=pass;
        this.displayName=displayName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        if (!super.equals(o)) return false;
        Users user = (Users) o;
        return login.equals(user.login) && password.equals(user.password) && displayName.equals(user.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password, displayName);
    }
}