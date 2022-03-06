package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Notification;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;
import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import ar.edu.itba.paw.webapp.dto.input.*;
import ar.edu.itba.paw.webapp.dto.output.*;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Image;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

@Path("users")
@Component
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private ListsService listsService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private WatchService watchService;
    @Autowired
    private CollaborativeListService collaborativeListService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ModeratorService moderatorService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";


    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response listUsers(@QueryParam("page") @DefaultValue(defaultPage) int page,
                              @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize,
                              @QueryParam("role") UserRole userRole,
                              @QueryParam("banned") Boolean banned) {
        final PageContainer<User> users = userService.getUsers(page, pageSize, userRole, banned);

        if (users.getElements().isEmpty()) {
            LOGGER.info("GET /users: Returning empty list.");
            return Response.noContent().build();
        }

        final List<UserDto> usersDto = UserDto.fromUserList(uriInfo, users.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserDto>>(usersDto) {
        });
        ResponseUtils.setPaginationLinks(response, users, uriInfo);

        LOGGER.info("GET /users: Returning page {} with {} results.", users.getCurrentPage(), users.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{username}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        LOGGER.info("GET /users/{}: Returning user {}.", user.getUsername(), user.getUsername());
        return Response.ok(UserDto.fromUser(uriInfo, user)).build();
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createUser(@Valid UserCreateDto userDto) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        if (userDto == null) {
            throw new EmptyBodyException();
        }

        final User user = userService.register(userDto.getEmail(), userDto.getUsername(), userDto.getPassword(), userDto.getName());

        LOGGER.info("POST /users: User {} created with id {}", user.getUsername(), user.getUserId());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build()).build();
    }

    @DELETE
    @Path("/{username}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteUser(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.deleteUser(user);

        LOGGER.info("DELETE /users/{username}: {} user deleted", username);
        return Response.noContent().build();
    }

    /**
     * Modify User
     * Change name
     * Change password
     */
    @PUT
    @Path("/{username}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response updatedUser(@PathParam("username") String username,
                                @Valid UserEditDto userEditDto) {
        if (userEditDto == null) {
            throw new EmptyBodyException();
        }

        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.updateUserData(user, userEditDto.getName());

        LOGGER.info("PUT /users/{username}: {} user updated", username);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{username}/password")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response updatePassword(@PathParam("username") String username,
                                   @Valid UserPasswordDto userPasswordDto) throws InvalidCurrentPasswordException {
        if (userPasswordDto == null) {
            throw new EmptyBodyException();
        }

        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.changePassword(user, userPasswordDto.getCurrentPassword(), userPasswordDto.getNewPassword());

        LOGGER.info("PUT /users/{username}/password: {} user password updated", username);
        return Response.noContent().build();
    }

    /**
     * Reset password - Recover
     */
    @POST
    @Path("/reset-password")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createPasswordResetToken(@Valid UserEmailDto userEmailDto) {
        if (userEmailDto == null) {
            throw new EmptyBodyException();
        }

        final User user = userService.getByEmail(userEmailDto.getEmail()).orElseThrow(EmailNotFoundException::new);

        final Token token = userService.forgotPassword(user);

        LOGGER.info("POST /users/reset-password: Token created for {} with expiry date on {}", user.getUsername(), token.getExpiryDate());
        return Response.created(uriInfo.getAbsolutePathBuilder().path("reset-password").build())
                .entity(TokenDto.fromToken(uriInfo, token))
                .build();
    }

    @PUT
    @Path("/reset-password")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response resetPassword(@Valid UserResetPasswordDto userResetPasswordDto) throws InvalidTokenException {
        if (userResetPasswordDto == null) {
            throw new EmptyBodyException();
        }

        final Token token = tokenService.getToken(userResetPasswordDto.getToken()).orElseThrow(TokenNotFoundException::new);

        userService.resetPassword(token, userResetPasswordDto.getNewPassword());

        LOGGER.info("PUT /users/reset-password: Password reset for {}", token.getUser().getUsername());
        return Response.noContent().build();
    }

    /**
     * User verification
     */
    @POST
    @Path("/verification-token")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response sendVerificationToken(@Valid UserEmailDto userEmailDto) throws EmailAlreadyVerifiedException {
        if (userEmailDto == null) {
            throw new EmptyBodyException();
        }

        final User user = userService.getByEmail(userEmailDto.getEmail()).orElseThrow(EmailNotFoundException::new);

        final Token token = userService.createVerificationToken(user);

        LOGGER.info("POST /users/verification: Token created for {} with expiry date on {}", user.getUsername(), token.getExpiryDate());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(token.getToken()).build()).build();
    }

    @PUT
    @Path("/verification-token/{token}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response verifyUser(@PathParam("token") String tokenString) throws InvalidTokenException {
        final Token token = tokenService.getToken(tokenString).orElseThrow(TokenNotFoundException::new);

        final User user = userService.confirmRegister(token);

        LOGGER.info("PUT /users/verification: User {} enabled", token.getUser().getUsername());
        return Response.noContent()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.createToken(user))
                .build();
    }

    /**
     * Profile image
     */
    @GET
    @Path("/{username}/image")
    @Produces(value = {"image/*", MediaType.APPLICATION_JSON})
    public Response getProfileImage(@PathParam("username") String username) throws ImageConversionException {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        byte[] profileImage = userService.getUserProfileImage(user.getImageId()).orElseThrow(ImageNotFoundException::new).getImageBlob();

        LOGGER.info("GET /users/{}/image: Returning user {} image", username, username);
        return Response.ok(profileImage).build();
    }

    @PUT
    @Path("/{username}/image")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    public Response updateProfileImage(@PathParam("username") String username,
                                       @Image @FormDataParam("image") final FormDataBodyPart image,
                                       @Size(max = 1024 * 1024 * 2) @FormDataParam("image") byte[] imageBytes) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.uploadUserProfileImage(user, imageBytes);

        LOGGER.info("PUT /users/{}/image: Returning user {} image", username, username);
        return Response.noContent().contentLocation(uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).path("image").build()).build();
    }

    @DELETE
    @Path("/{username}/image")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteProfileImage(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.deleteUserProfileImage(user);

        LOGGER.info("DELETE /users/{}/image: User {} profile image deleted", username, username);
        return Response.noContent().build();
    }

    /**
     * Mod Request and Delete Mod Role
     */
    @POST
    @Path("/{username}/mod-requests")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createModRequest(@PathParam("username") String username) throws ModRequestAlreadyExistsException, UserAlreadyIsModException {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        ModRequest modRequest = moderatorService.addModRequest(user);

        LOGGER.info("POST /users/{}/mod-requests: Mod request added to user {}", username, username);
        return Response.created(uriInfo.getBaseUriBuilder().path("mods-requests").path(String.valueOf(modRequest.getRequestId())).build()).build();
    }

    @DELETE
    @Path("{username}/mod")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response removeMod(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        moderatorService.removeMod(user);

        LOGGER.info("DELETE /users/{}/mod: Mod role removed to user {}", username, username);
        return Response.noContent().build();
    }

    /**
     * Locked - ban user
     */
    @PUT
    @Path("{username}/locked")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response banUser(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.banUser(user);

        LOGGER.info("DELETE /users/{}/locked: User {} banned", username, username);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{username}/locked")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response unbanUser(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.unbanUser(user);

        LOGGER.info("DELETE /users/{}/locked: User {} unbanned", username, username);
        return Response.noContent().build();
    }

    /**
     * Favorite Media
     */
    @GET
    @Path("/{username}/favorite-media")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserFavoriteMedia(@PathParam("username") String username,
                                         @QueryParam("page") @DefaultValue(defaultPage) int page,
                                         @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<Media> favoriteMedia = favoriteService.getUserFavoriteMedia(user, page, pageSize);

        if (favoriteMedia.getElements().isEmpty()) {
            LOGGER.info("GET /users/{}/favorite-media: Returning empty list.", username);
            return Response.noContent().build();
        }

        final List<MediaFavoriteDto> mediaFavoriteDtoList = MediaFavoriteDto.fromMediaList(uriInfo, favoriteMedia.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaFavoriteDto>>(mediaFavoriteDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, favoriteMedia, uriInfo);

        LOGGER.info("GET /users/{}/favorite-media: Returning page {} with {} results.", username, favoriteMedia.getCurrentPage(), favoriteMedia.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{username}/favorite-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response isFavoriteMedia(@PathParam("username") String username,
                                    @PathParam("mediaId") int mediaId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        if (!favoriteService.isFavorite(media, user)) {
            LOGGER.info("GET /users/{}/favorite-media/{}: media {} is not favorite of {}.", username, mediaId, mediaId, username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("GET /users/{}/favorite-media/{}: media {} is favorite of {}.", username, mediaId, mediaId, username);
        return Response.ok(MediaFavoriteDto.fromMediaAndUser(uriInfo, media, user)).build();
    }

    @PUT
    @Path("/{username}/favorite-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response addMediaToFavorites(@PathParam("username") String username,
                                        @PathParam("mediaId") int mediaId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        favoriteService.addMediaToFav(media, user);

        LOGGER.info("PUT /users/{}/favorite-media/{}: media {} added to {}'s favorites.", username, mediaId, mediaId, username);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{username}/favorite-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response removeMediaFromFavorites(@PathParam("username") String username,
                                             @PathParam("mediaId") int mediaId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        favoriteService.deleteMediaFromFav(media, user);

        LOGGER.info("DELETE /users/{}/favorite-media/{}: media {} removed from {}'s favorites.", username, mediaId, mediaId, username);
        return Response.noContent().build();
    }

    /**
     * Watched Media
     */
    @GET
    @Path("/{username}/watched-media")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserWatchedMedia(@PathParam("username") String username,
                                        @QueryParam("page") @DefaultValue(defaultPage) int page,
                                        @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<WatchedMedia> watchedMedia = watchService.getWatchedMedia(user, page, pageSize);

        if (watchedMedia.getElements().isEmpty()) {
            LOGGER.info("GET /users/{}/watched-media: Returning empty list.", username);
            return Response.noContent().build();
        }

        final List<MediaWatchedDto> mediaWatchedDtoList = MediaWatchedDto.fromMediaList(uriInfo, watchedMedia.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaWatchedDto>>(mediaWatchedDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, watchedMedia, uriInfo);

        LOGGER.info("GET /users/{}/watched-media: Returning page {} with {} results.", username, watchedMedia.getCurrentPage(), watchedMedia.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{username}/watched-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response isWatchedMedia(@PathParam("username") String username,
                                   @PathParam("mediaId") int mediaId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        final Optional<WatchedMedia> watchedMedia = watchService.getWatchedMedia(user, media);

        if (!watchedMedia.isPresent()) {
            LOGGER.info("GET /users/{}/watched-media/{}: media {} is not watched by {}.", username, mediaId, mediaId, username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("GET /users/{}/watched-media/{}: media {} is watched by {} on {}.", username, mediaId, mediaId, username, watchedMedia.get().getWatchDate());
        return Response.ok(MediaWatchedDto.fromWatchedMediaAndUser(uriInfo, watchedMedia.get(), user)).build();
    }

    @PUT
    @Path("/{username}/watched-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response addMediaToWatched(@PathParam("username") String username,
                                      @PathParam("mediaId") int mediaId,
                                      @Valid DateTimeDto dateTimeDto) {
        if (dateTimeDto == null) {
            throw new EmptyBodyException();
        }

        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        watchService.addWatchedMedia(media, user, dateTimeDto.getDateTime());

        LOGGER.info("PUT /users/{}/watched-media/{}: media {} added to {}'s watched on {}.", username, mediaId, mediaId, username, dateTimeDto.getDateTime().toLocalDate());
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{username}/watched-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response removeMediaFromWatched(@PathParam("username") String username,
                                           @PathParam("mediaId") int mediaId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        watchService.deleteWatchedMedia(media, user);

        LOGGER.info("DELETE /users/{}/watched-media/{}: media {} removed from {}'s watched.", username, mediaId, mediaId, username);
        return Response.noContent().build();
    }

    /**
     * To Watch Media
     */
    @GET
    @Path("/{username}/to-watch-media")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserToWatchMedia(@PathParam("username") String username,
                                        @QueryParam("page") @DefaultValue(defaultPage) int page,
                                        @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<Media> toWatchMedia = watchService.getToWatchMedia(user, page, pageSize);

        if (toWatchMedia.getElements().isEmpty()) {
            LOGGER.info("GET /users/{}/to-watch-media: Returning empty list.", username);
            return Response.noContent().build();
        }

        final List<MediaToWatchDto> mediaToWatchDtoList = MediaToWatchDto.fromMediaList(uriInfo, toWatchMedia.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaToWatchDto>>(mediaToWatchDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, toWatchMedia, uriInfo);

        LOGGER.info("GET /users/{}/to-watch-media: Returning page {} with {} results.", username, toWatchMedia.getCurrentPage(), toWatchMedia.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{username}/to-watch-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response isToWatchMedia(@PathParam("username") String username,
                                   @PathParam("mediaId") int mediaId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        if (!watchService.isToWatch(media, user)) {
            LOGGER.info("GET /users/{}/to-watch-media/{}: media {} is not to watch by {}.", username, mediaId, mediaId, username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("GET /users/{}/to-watch-media/{}: media {} is to watch by {}.", username, mediaId, mediaId, username);
        return Response.ok(MediaToWatchDto.fromMediaAndUser(uriInfo, media, user)).build();
    }

    @PUT
    @Path("/{username}/to-watch-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response addMediaToWatch(@PathParam("username") String username,
                                    @PathParam("mediaId") int mediaId,
                                    DateTimeDto dateTimeDto) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        watchService.addMediaToWatch(media, user);

        LOGGER.info("PUT /users/{}/to-watch-media/{}: media {} added to {}'s to watch.", username, mediaId, mediaId, username);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{username}/to-watch-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response removeMediaFromToWatch(@PathParam("username") String username,
                                           @PathParam("mediaId") int mediaId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        watchService.deleteToWatchMedia(media, user);

        LOGGER.info("DELETE /users/{}/to-watch-media/{}: media {} removed from {}'s to watch.", username, mediaId, mediaId, username);
        return Response.noContent().build();
    }

    /**
     * Favorite Lists
     */
    @GET
    @Path("/{username}/favorite-lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserFavoriteLists(@PathParam("username") String username,
                                         @QueryParam("page") @DefaultValue(defaultPage) int page,
                                         @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        //TODO
        return null;
    }

    @GET
    @Path("/{username}/favorite-lists/{list-id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response isFavoriteList(@PathParam("username") String username,
                                   @PathParam("list-id") int listId) {
        //TODO
        return null;
    }

    @PUT
    @Path("/{username}/favorite-lists/{list-id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response addListToFavorites(@PathParam("username") String username,
                                       @PathParam("list-id") int listId) {
        //TODO
        return null;
    }

    @DELETE
    @Path("/{username}/favorite-lists/{list-id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response removeListFromFavorites(@PathParam("username") String username,
                                            @PathParam("list-id") int listId) {
        //TODO
        return null;
    }

    /**
     * Notifications
     */
    @GET
    @Path("/{username}/notifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserNotifications(@PathParam("username") String username,
                                         @QueryParam("page") @DefaultValue(defaultPage) int page,
                                         @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<Notification> notifications = commentService.getUserListsCommentsNotifications(user, page, pageSize);

        if (notifications.getElements().isEmpty()) {
            LOGGER.info("GET /users/{}/notifications: Returning empty list.", username);
            return Response.noContent().build();
        }

        final List<NotificationDto> notificationDtoList = NotificationDto.fromNotificationList(uriInfo, notifications.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<NotificationDto>>(notificationDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, notifications, uriInfo);

        LOGGER.info("GET /users/{}/notifications: Returning page {} with {} results.", username, notifications.getCurrentPage(), notifications.getElements().size());
        return response.build();
    }

    /**
     * Collab Requests
     */
    @GET
    @Path("/{username}/collab-requests")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserCollaborationRequests(@PathParam("username") String username,
                                                 @QueryParam("page") @DefaultValue(defaultPage) int page,
                                                 @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        //TODO
        return null;
    }

    /**
     * Recommended Content
     */
    @GET
    @Path("/{username}/recommended-media")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserRecommendedMedia(@PathParam("username") String username,
                                            @QueryParam("page") @DefaultValue(defaultPageSize) int page,
                                            @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize,
                                            @QueryParam("type") @NotNull ar.edu.itba.paw.models.media.MediaType mediaType) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<Media> recommendedMedia = favoriteService.getRecommendationsBasedOnFavMedia(mediaType, user, page, pageSize);

        if (recommendedMedia.getElements().isEmpty()) {
            LOGGER.info("GET /users/{}/recommended-media: Returning empty list", username);
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtoList = MediaDto.fromMediaList(uriInfo, recommendedMedia.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, recommendedMedia, uriInfo);

        LOGGER.info("GET /users/{}/recommended-media: Returning page {} with {} results.", username, recommendedMedia.getCurrentPage(), recommendedMedia.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{username}/recommended-lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserRecommendedLists(@PathParam("username") String username,
                                            @QueryParam("page") @DefaultValue(defaultPageSize) int page,
                                            @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<MediaList> recommendedLists = favoriteService.getRecommendationsBasedOnFavLists(user, page, pageSize);

        if(recommendedLists.getElements().isEmpty()) {
            LOGGER.info("GET /users/{}/recommended-lists: Returning empty list", username);
            return Response.noContent().build();
        }

        //TODO
        return null;
    }
}
