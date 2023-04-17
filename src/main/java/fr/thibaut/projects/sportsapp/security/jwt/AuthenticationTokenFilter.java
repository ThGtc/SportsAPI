package fr.thibaut.projects.sportsapp.security.jwt;

import fr.thibaut.projects.sportsapp.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);
    @Autowired
    private JsonWebTokenUtils jsonWebTokenUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jsonWebToken = parseJsonWebToken(request);
            if (jsonWebToken != null && jsonWebTokenUtils.validateToken(jsonWebToken)) {
                String username = jsonWebTokenUtils.getUserNameFromToken(jsonWebToken);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Impossible d'authentifier l'utilisateur: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJsonWebToken(HttpServletRequest request) {
        String jsonWebToken = jsonWebTokenUtils.getJsonWebTokenFromCookies(request);
        return jsonWebToken;
    }
}
