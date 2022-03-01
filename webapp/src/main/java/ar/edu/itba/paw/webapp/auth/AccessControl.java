package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.CollaborativeListService;
import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.comment.Notification;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

/**
 * Ignore "Method is never used"
 */
@Component
public class AccessControl {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CollaborativeListService collaborativeListService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ListsService listsService;
    @Autowired
    private UserService userService;

    public boolean checkUser(HttpServletRequest request, String username) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        return userDetails.getUsername().equals(username);
    }

    @Transactional(readOnly = true)
    public boolean checkNotificationOwner(HttpServletRequest request, int notificationId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final Notification notification = commentService.getListCommentNotification(notificationId).orElse(null);
        if (notification == null) {
            return true; // Jersey will throw 404 Response
        }
        return userDetails.getUsername().equals(notification.getListComment().getMediaList().getUser().getUsername());
    }

    @Transactional(readOnly = true)
    public boolean checkListOwner(HttpServletRequest request, int listId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final MediaList mediaList = listsService.getMediaListById(listId).orElse(null);
        if (mediaList == null) {
            return true; // Jersey will throw 404 Response
        }
        return userDetails.getUsername().equals(mediaList.getUser().getUsername());
    }

    // It is not !checkListOwner because first two cases are the same.
    @Transactional(readOnly = true)
    public boolean checkListNotOwner(HttpServletRequest request, int listId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final MediaList mediaList = listsService.getMediaListById(listId).orElse(null);
        if (mediaList == null) {
            return true; // Jersey will throw 404 Response
        }
        return !userDetails.getUsername().equals(mediaList.getUser().getUsername());
    }

    @Transactional(readOnly = true)
    public boolean checkListCollaborator(HttpServletRequest request, int listId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final User user = userService.getByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return false; // Jersey will throw 404 Response
        }
        final MediaList mediaList = listsService.getMediaListById(listId).orElse(null);
        if (mediaList == null) {
            return true;
        }
        return listsService.canEditList(user, mediaList);
    }

    @Transactional(readOnly = true)
    public boolean checkCollabRequestListOwner(HttpServletRequest request, int requestId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final Request collabRequest = collaborativeListService.getById(requestId).orElse(null);
        if (collabRequest == null) {
            return true;
        }
        return userDetails.getUsername().equals(collabRequest.getMediaList().getUser().getUsername());
    }

    @Transactional(readOnly = true)
    public boolean checkMediaCommentOwner(HttpServletRequest request, int commentId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final MediaComment mediaComment = commentService.getMediaCommentById(commentId).orElse(null);
        if (mediaComment == null) {
            return true; // Jersey will throw 404 Response
        }
        return userDetails.getUsername().equals(mediaComment.getUser().getUsername());
    }

    // It is not !checkMediaCommentOwner because first two cases are the same.
    @Transactional(readOnly = true)
    public boolean checkMediaCommentNotOwner(HttpServletRequest request, int commentId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final MediaComment mediaComment = commentService.getMediaCommentById(commentId).orElse(null);
        if (mediaComment == null) {
            return true; // Jersey will throw 404 Response
        }
        return !userDetails.getUsername().equals(mediaComment.getUser().getUsername());
    }

    @Transactional(readOnly = true)
    public boolean checkListCommentOwner(HttpServletRequest request, int commentId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final ListComment listComment = commentService.getListCommentById(commentId).orElse(null);
        if (listComment == null) {
            return true; // Jersey will throw 404 Response
        }
        return userDetails.getUsername().equals(listComment.getUser().getUsername());
    }

    // It is not !checkListCommentOwner because first two cases are the same.
    @Transactional(readOnly = true)
    public boolean checkListCommentNotOwner(HttpServletRequest request, int commentId) {
        final UserDetails userDetails = getUserDetailsFromRequest(request);
        if (userDetails == null) {
            return false;
        }
        final ListComment listComment = commentService.getListCommentById(commentId).orElse(null);
        if (listComment == null) {
            return true; // Jersey will throw 404 Response
        }
        return !userDetails.getUsername().equals(listComment.getUser().getUsername());
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
