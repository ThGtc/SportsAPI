package fr.thibaut.projects.sportsapp.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.thibaut.projects.sportsapp.model.Sport;
import fr.thibaut.projects.sportsapp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Long id;
    private final String pseudo;
    private final String email;
    @JsonIgnore
    private final String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Sport favoriteSport;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String pseudo, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UserDetailsImpl(Long id, String pseudo, String email, String password, String firstName, String lastName, String birthDate, Sport favoriteSport) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.favoriteSport = favoriteSport;
    }

    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(
                user.getId(),
                user.getPseudo(),
                user.getMail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getFavoriteSport());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Sport getFavoriteSport() {
        return favoriteSport;
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
