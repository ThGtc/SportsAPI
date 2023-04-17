package fr.thibaut.projects.sportsapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sport_id", nullable = true)
    private Sport sport;

    @Column(name = "duration")
    private long duration;

    @Column(name = "distance")
    private double distance;

    @Column(name = "description")
    private String description;

    @Column(name = "activity_date", nullable = false)
    private String activityDate;

    @Column(name = "related_picture")
    private String relatedPic;

    @Column(name = "is_private")
    private boolean isPrivate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "posted_by", nullable = true)
    private User postedBy;

    public Activity() {
    }

    @JsonCreator
    public Activity(String title, Sport sport, long duration, double distance, String description,
                    String activityDate, String relatedPic, boolean isPrivate, User postedBy) {
        this.title = title;
        this.sport = sport;
        this.duration = duration;
        this.distance = distance;
        this.description = description;
        this.activityDate = activityDate;
        this.relatedPic = relatedPic;
        this.isPrivate = isPrivate;
        this.postedBy = postedBy;
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

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getRelatedPic() {
        return relatedPic;
    }

    public void setRelatedPic(String relatedPic) {
        this.relatedPic = relatedPic;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean Private) {
        isPrivate = Private;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sport=" + sport +
                ", duration=" + duration +
                ", distance=" + distance +
                ", description='" + description + '\'' +
                ", activityDate='" + activityDate + '\'' +
                ", relatedPic='" + relatedPic + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
