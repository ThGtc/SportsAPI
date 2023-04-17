package fr.thibaut.projects.sportsapp.payload.response;

import fr.thibaut.projects.sportsapp.model.Sport;

public class UserInfoResponse {
    private Long id;
    private String pseudo;
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Sport favoriteSport;

    public UserInfoResponse(Long id, String pseudo, String email, String firstName, String lastName, String birthDate, Sport favoriteSport) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.favoriteSport = favoriteSport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Sport getFavoriteSport() {
        return favoriteSport;
    }

    public void setFavoriteSport(Sport favoriteSport) {
        this.favoriteSport = favoriteSport;
    }
}
