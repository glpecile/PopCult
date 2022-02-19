package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Country;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.user.User;

import java.time.LocalDateTime;

public class InstanceProvider {

    private static final int ALREADY_EXISTS_USER_ID = 4;
    private static final String ALREADY_EXISTS_EMAIL = "email@email.com";
    private static final String ALREADY_EXISTS_USERNAME = "username";
    private static final String ALREADY_EXISTS_PASSWORD = "password";
    private static final String ALREADY_EXISTS_NAME = "name";

    private static final int ALREADY_EXISTS_USER_ID_WITH_MOD_REQUEST = 5;
    private static final String ALREADY_EXISTS_EMAIL_WITH_MOD_REQUEST = "mod@request.com";
    private static final String ALREADY_EXISTS_USERNAME_WITH_MOD_REQUEST = "modRequestUser";
    private static final String ALREADY_EXISTS_PASSWORD_WITH_MOD_REQUEST = "password";
    private static final String ALREADY_EXISTS_NAME_WITH_MOD_REQUEST = "name";

    private static final int ALREADY_EXISTS_USER_ID_NO_COLLABORATOR = 5;
    private static final String ALREADY_EXISTS_EMAIL_NO_COLLABORATOR = "mod@request.com";
    private static final String ALREADY_EXISTS_USERNAME_NO_COLLABORATOR = "modRequestUser";
    private static final String ALREADY_EXISTS_PASSWORD_NO_COLLABORATOR = "password";
    private static final String ALREADY_EXISTS_NAME_NO_COLLABORATOR = "name";

    private static final int ALREADY_EXISTS_USER_ID_LIST_OWNER = 10;
    private static final String ALREADY_EXISTS_EMAIL_LIST_OWNER = "popcult.paw@gmail.com";
    private static final String ALREADY_EXISTS_USERNAME_LIST_OWNER = "PopCult";
    private static final String ALREADY_EXISTS_PASSWORD_LIST_OWNER = "password";
    private static final String ALREADY_EXISTS_NAME_LIST_OWNER = "PopCult";

    private static final String COMMENT = "Comment";

    /* default */ static final int ALREADY_EXISTS_MEDIA_ID = 1;
    private static final int ALREADY_EXISTS_LIKED_MEDIA_ID = 2;
    private static final int ALREADY_EXISTS_TO_WATCH_MEDIA_ID = 2;
    private static final int ALREADY_EXISTS_WATCHED_MEDIA_ID = 3;

    /* default */ static final int ALREADY_EXISTS_LIST_ID = 2;
    private static final int MEDIA_ALREADY_IN_LIST_ID = 29;
    private static final int ALREADY_EXISTS_LIKED_LIST_ID = 3;
    private static final int ALREADY_EXISTS_CAN_EDIT_LIST_ID = 3;

    /* default */ static final int ALREADY_EXISTS_MEDIA_COMMENT_ID = 2;
    /* default */ static final int ALREADY_EXISTS_LIST_COMMENT_ID = 2;

    /* default */ static final int ALREADY_EXISTS_COLLAB_ID = 2;

    /* default */ static final int ALREADY_EXISTS_IMAGE_ID = 2;

    /* default */ static final int ALREADY_EXISTS_LIST_REPORT_ID = 2;
    /* default */ static final int ALREADY_EXISTS_LIST_COMMENT_REPORT_ID = 2;
    /* default */ static final int ALREADY_EXISTS_MEDIA_COMMENT_REPORT_ID = 2;

    private static final int ALREADY_REPORTED_LIST_ID = 3;
    private static final int ALREADY_REPORTED_LIST_COMMENT_ID = 3;
    private static final int ALREADY_REPORTED_MEDIA_COMMENT_ID = 3;

    private InstanceProvider() {
        throw new AssertionError();
    }

    public static User getUser() {
        return new User.Builder(ALREADY_EXISTS_EMAIL, ALREADY_EXISTS_USERNAME, ALREADY_EXISTS_PASSWORD, ALREADY_EXISTS_NAME)
                .userId(ALREADY_EXISTS_USER_ID)
                .build();
    }

    public static User getUserWithModRequest() {
        return new User.Builder(ALREADY_EXISTS_EMAIL_WITH_MOD_REQUEST, ALREADY_EXISTS_USERNAME_WITH_MOD_REQUEST, ALREADY_EXISTS_PASSWORD_WITH_MOD_REQUEST, ALREADY_EXISTS_NAME_WITH_MOD_REQUEST)
                .userId(ALREADY_EXISTS_USER_ID_WITH_MOD_REQUEST)
                .build();
    }

    public static User getOwnerListUser() {
        return new User.Builder(ALREADY_EXISTS_EMAIL_LIST_OWNER, ALREADY_EXISTS_USERNAME_LIST_OWNER, ALREADY_EXISTS_PASSWORD_LIST_OWNER, ALREADY_EXISTS_NAME_LIST_OWNER)
                .userId(ALREADY_EXISTS_USER_ID_LIST_OWNER)
                .build();
    }

    public static User getUserCollaborator() {
        return getUser();
    }

    public static User getNoCollaboratorUser() {
        return new User.Builder(ALREADY_EXISTS_EMAIL_NO_COLLABORATOR, ALREADY_EXISTS_USERNAME_NO_COLLABORATOR, ALREADY_EXISTS_PASSWORD_NO_COLLABORATOR, ALREADY_EXISTS_NAME_NO_COLLABORATOR)
                .userId(ALREADY_EXISTS_USER_ID_NO_COLLABORATOR)
                .build();
    }

    public static Media getMedia() {
        return new Media(ALREADY_EXISTS_MEDIA_ID, MediaType.SERIE, "House", "...", "", 7788, null, 8, Country.US);
    }

    public static MediaList getMediaList() {
        return new MediaList(ALREADY_EXISTS_LIST_ID, getOwnerListUser(), "Kids Movies", "...", null, true, false);
    }

    public static ListComment getListComment() {
        return new ListComment(ALREADY_EXISTS_LIST_COMMENT_ID, getUser(), COMMENT, LocalDateTime.now(), getMediaList());
    }

    public static MediaComment getMediaComment() {
        return new MediaComment(ALREADY_EXISTS_MEDIA_COMMENT_ID, getUser(), COMMENT, LocalDateTime.now(), getMedia());
    }

    public static Media getLikedMedia() {
        return new Media(ALREADY_EXISTS_LIKED_MEDIA_ID, MediaType.SERIE, "Sons of Anarchy", "...", "", 4140, null, 7, Country.US);
    }

    public static MediaList getLikedMediaList() {
        return new MediaList(ALREADY_EXISTS_LIKED_LIST_ID, getUser(), "Movies to Enjoy Alone", "...", null, true, false);
    }

    public static Media getMediaAlreadyInList() {
        return new Media(MEDIA_ALREADY_IN_LIST_ID, MediaType.FILMS, "Shrek", "...", "", 90, null, 0, Country.US);
    }

    public static MediaList getCanEditMediaList() {
        return new MediaList(ALREADY_EXISTS_CAN_EDIT_LIST_ID, getOwnerListUser(), "Movies to Enjoy Alone", "...", null, true, false);
    }

    public static Media getToWatchMedia() {
        return new Media(ALREADY_EXISTS_TO_WATCH_MEDIA_ID, MediaType.SERIE, "Sons of Anarchy", "...", "", 4140, null, 7, Country.US);
    }

    public static Media getWatchedMedia() {
        return new Media(ALREADY_EXISTS_WATCHED_MEDIA_ID, MediaType.SERIE, "Revolution", "...", "", 1764, null, 2, Country.US);
    }

    public static MediaList getAlreadyReportedList() {
        return new MediaList(ALREADY_REPORTED_LIST_ID, getOwnerListUser(), "...", "...", null, true, false);
    }

    public static ListComment getAlreadyReportedListComment() {
        return new ListComment(ALREADY_REPORTED_LIST_COMMENT_ID, getUser(), COMMENT, LocalDateTime.now(), getMediaList());
    }

    public static MediaComment getAlreadyReportedMediaComment() {
        return new MediaComment(ALREADY_REPORTED_MEDIA_COMMENT_ID, getUser(), COMMENT, LocalDateTime.now(), getMedia());
    }
}
