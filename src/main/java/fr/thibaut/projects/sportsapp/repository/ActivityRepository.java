package fr.thibaut.projects.sportsapp.repository;

import fr.thibaut.projects.sportsapp.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByisPrivate(boolean isPrivate);

    List<Activity> findByTitleContaining(String title);

}
