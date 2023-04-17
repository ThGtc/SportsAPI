package fr.thibaut.projects.sportsapp.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "sports")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "includes_distance", nullable = false)
    private boolean includesDistance;

    @Column(name = "related_logo")
    private String relatedLogo;

    @OneToMany(mappedBy = "sport")
    private Collection<Activity> activity;

    @OneToMany(mappedBy = "favoriteSport")
    private Collection<User> user;

    public Sport() {
    }

    public Sport(String title, boolean includesDistance) {
        this.title = title;
        this.includesDistance = includesDistance;
    }

    public Sport(String title, boolean includesDistance, String relatedLogo) {
        this.title = title;
        this.includesDistance = includesDistance;
        this.relatedLogo = relatedLogo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIncludesDistance() {
        return includesDistance;
    }

    public void setIncludesDistance(boolean includesDistance) {
        this.includesDistance = includesDistance;
    }

    public String getRelatedLogo() {
        return relatedLogo;
    }

    public void setRelatedLogo(String relatedLogo) {
        this.relatedLogo = relatedLogo;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", hasDistance=" + includesDistance +
                ", relatedLogo='" + relatedLogo + '\'' +
                '}';
    }
}
