package fr.thibaut.projects.sportsapp.controller;

import fr.thibaut.projects.sportsapp.model.User;
import fr.thibaut.projects.sportsapp.payload.request.LoginRequest;
import fr.thibaut.projects.sportsapp.payload.request.SignupRequest;
import fr.thibaut.projects.sportsapp.payload.response.MessageResponse;
import fr.thibaut.projects.sportsapp.payload.response.UserInfoResponse;
import fr.thibaut.projects.sportsapp.repository.UserRepository;
import fr.thibaut.projects.sportsapp.security.jwt.JsonWebTokenUtils;
import fr.thibaut.projects.sportsapp.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JsonWebTokenUtils jsonWebTokenUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getPseudo(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jsonWebTokenUtils.generateJsonWebTokenCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getPseudo(),
                        userDetails.getEmail(),
                        userDetails.getFirstName(),
                        userDetails.getLastName(),
                        userDetails.getBirthDate(),
                        userDetails.getFavoriteSport()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByPseudo(signUpRequest.getPseudo())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Ce pseudo est déjà choisi !"));
        }

        if (userRepository.existsByMail(signUpRequest.getMail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("L'email est correspond déjà à un compte !"));
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getPseudo(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getMail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getBirthDate(),
                signUpRequest.getFavoriteSport());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Inscription effectuée avec succès !"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jsonWebTokenUtils.getCleanJsonWebTokenCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Vous avez bien été déconnecté !"));
    }

}
