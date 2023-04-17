package fr.thibaut.projects.sportsapp.security.services;

import fr.thibaut.projects.sportsapp.model.User;
import fr.thibaut.projects.sportsapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        User user = userRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur avec ce pseudo : " + pseudo));

        return UserDetailsImpl.build(user);
    }
}