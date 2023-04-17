package fr.thibaut.projects.sportsapp.repository;

import fr.thibaut.projects.sportsapp.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {

    List<Sport> findByTitleContaining(String title);


}
