package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.models.comment.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

@Component
public class AccessControl {

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    CommentService commentService;

    public boolean checkUser(HttpServletRequest request, String usernameFromPath) {
        UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        return userDetails.getUsername().equals(usernameFromPath);
    }

    @Transactional(readOnly = true)
    public boolean checkNotificationOwner(HttpServletRequest request, int notificationId) {
        UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        Notification notification = commentService.getListCommentNotification(notificationId).orElse(null);
        if (notification == null) {
            return true;    //Duda conceptual. El exceptionMapper no funciona aca, por lo que no se puede lanzar la excepción de que no existe la
                            // notificación. Pero en el caso de no existir, lo correcto sería devolver 404 (no existe) o 401 (no autorizado).
                            // Retornando true se devuelve el 404
        }
        return userDetails.getUsername().equals(notification.getListComment().getMediaList().getUser().getUsername());
    }

    private UserDetails getUserDetailsFromRequest(HttpServletRequest request) {
        final String token = parseAuthHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (token == null) {
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
