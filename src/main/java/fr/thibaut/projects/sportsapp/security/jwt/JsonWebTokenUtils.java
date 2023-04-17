package fr.thibaut.projects.sportsapp.security.jwt;

import fr.thibaut.projects.sportsapp.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Date;

@Component
public class JsonWebTokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenUtils.class);

    @Value("${sports.app.jsonWebTokenSecretKey}")
    private String jsonWebTokenSecretKey;

    @Value("${sports.app.jsonWebTokenExpirationMs}")
    private int jsonWebTokenExpirationMs;

    @Value("${sports.app.jsonWebTokenCookieName}")
    private String jsonWebTokenCookie;

    public String getJsonWebTokenFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jsonWebTokenCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateJsonWebTokenCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getPseudo());
        ResponseCookie cookie = ResponseCookie.from(jsonWebTokenCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJsonWebTokenCookie() {
        ResponseCookie cookie = ResponseCookie.from(jsonWebTokenCookie, null).path("/api").build();
        return cookie;
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(jsonWebTokenSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jsonWebTokenSecretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Mauvaise signature du JSON Web Token : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Jeton JSON Web Token invalide : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Le jeton JSON Web Token a expiré: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Le jeton JSON Web Token n'est pas supporté: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("La chaîne de caractère claims du JSON Web Token est vide: {}", e.getMessage());
        }
        return false;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jsonWebTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jsonWebTokenSecretKey)
                .compact();
    }

}
