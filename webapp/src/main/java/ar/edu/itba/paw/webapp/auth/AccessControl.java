package ar.edu.itba.paw.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

@Component
public class AccessControl {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public boolean checkUser(HttpServletRequest request, String usernameFromPath) {
        UserDetails userDetails = getUserDetailsFromRequest(request);
        if(userDetails == null) {
            return false;
        }
        return userDetails.getUsername().equals(usernameFromPath);
    }

    private UserDetails getUserDetailsFromRequest(HttpServletRequest request) {
        final String token = parseAuthHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        if(token == null) {
            return null;
        }

        return jwtTokenUtil.parseToken(token);
    }

    private String parseAuthHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        return header.split(" ")[1].trim();
    }
}
