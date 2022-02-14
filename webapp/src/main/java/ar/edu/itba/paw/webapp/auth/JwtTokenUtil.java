package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * https://github.com/jwtk/jjwt
 * https://github.com/Yoh0xFF/java-spring-security-example
 */
@Component
public class JwtTokenUtil {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private static final int EXPIRY_TIME = 7 * 24 * 60 * 60 * 1000; //1 week (in millis)

    private final Key jwtKey;

    public JwtTokenUtil(Resource jwtKeyResource) throws IOException {
        this.jwtKey = Keys.hmacShaKeyFor(
                FileCopyUtils.copyToString(new InputStreamReader(jwtKeyResource.getInputStream()))
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * jws: Json Web Signature (https://datatracker.ietf.org/doc/html/rfc7515)
     */
    public UserDetails parseToken(String jws) {
        try {
            final Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(jws).getBody();

            if (new Date(System.currentTimeMillis()).after(claims.getExpiration())) {
                return null;
            }

            final String username = claims.getSubject();

            return userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims();

        claims.setSubject(user.getUsername());
        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME))
                .signWith(jwtKey)
                .compact();
    }
}
