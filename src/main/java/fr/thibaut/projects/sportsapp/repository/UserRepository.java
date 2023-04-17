package fr.thibaut.projects.sportsapp.repository;

import fr.thibaut.projects.sportsapp.model.Sport;
import fr.thibaut.projects.sportsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByFavoriteSport(Sport sport);

    List<User> findByPseudoContaining(String pseud);

    Optional<User> findByPseudo(String pseudo);

    Boolean existsByPseudo(String pseudo);

    Boolean existsByMail(String mail);
}
