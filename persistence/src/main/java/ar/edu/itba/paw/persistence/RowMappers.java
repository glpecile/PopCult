package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.StaffMember;
import ar.edu.itba.paw.models.staff.Studio;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;
import org.springframework.jdbc.core.RowMapper;

public class RowMappers {
    /**
     * General Purpose RowMappers.
     */
    public static final RowMapper<Integer> COUNT_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("count");

    /**
     * Media RowMappers.
     */
    public static final RowMapper<Media> MEDIA_ROW_MAPPER =
            (rs, rowNum) -> new Media(
                    rs.getInt("mediaId"),
                    rs.getInt("type"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getInt("length"),
                    rs.getDate("releaseDate"),
                    rs.getInt("seasons"),
                    rs.getInt("country"));

    public static final RowMapper<Integer> MEDIA_ID_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("mediaId");

    /**
     * Genre RowMappers.
     */
    public static final RowMapper<String> STRING_NAME_ROW_MAPPER =
            (rs, rowNum) -> rs.getString("name");

    /**
     * Lists RowMappers.
     */
    public static final RowMapper<MediaList> MEDIA_LIST_ROW_MAPPER =
            (rs, rowNum) -> new MediaList(
                    rs.getInt("mediaListId"),
                    rs.getInt("userId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("creationDate"),
                    rs.getBoolean("visibility"),
                    rs.getBoolean("collaborative"));

    public static final RowMapper<Integer> MEDIA_LIST_ID_MAPPER =
            (rs, rowNum) -> rs.getInt("mediaListId");

    /**
     * Staff RowMappers.
     */
    public static final RowMapper<StaffMember> STAFF_MEMBER_ROW_MAPPER =
            (rs, rowNum) -> new StaffMember(rs.getInt("staffMemberId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("image"));

    public static final RowMapper<Actor> ACTOR_ROW_MAPPER =
            (rs, rowNum) -> new Actor(new StaffMember(rs.getInt("staffMemberId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("image")),
                    rs.getString("characterName"));
    /**
     * Staff RowMappers.
     */
    public static final RowMapper<Studio> STUDIO_ROW_MAPPER = (rs, rowNum) -> new Studio(
            rs.getInt("studioId"),
            rs.getString("name"),
            rs.getString("image"));
    /**
     * User RowMappers.
     */
    public static final RowMapper<User> USER_ROW_MAPPER =
            (rs, rowNum) -> new User(
                    rs.getInt("userId"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    "name", //TODO
                    "profilePhoto",
                    rs.getBoolean("enabled"));
    /**
     * Token RowMappers.
     */
    public static final RowMapper<Token> TOKEN_ROW_MAPPER =
            (rs, rowNum) -> new Token(
                    rs.getInt("userId"),
                    rs.getString("token"),
                    rs.getDate("expiryDate"));

    /**
     * Watched Media RowMappers.
     */
    public static final RowMapper<WatchedMedia> WATCHED_MEDIA_ROW_MAPPER =
            (rs, rowNum) -> new WatchedMedia(
                    rs.getInt("mediaId"),
                    rs.getInt("type"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getInt("length"),
                    rs.getDate("releaseDate"),
                    rs.getInt("seasons"),
                    rs.getInt("country"),
                    rs.getDate("watchdate"));
}



