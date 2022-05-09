package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
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
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotEmptyBody;
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
                              @QueryParam("banned") Boolean banned,
                              @QueryParam("query") String term,
                              @QueryParam("not-collab-in-list") Integer listId) {
        final PageContainer<User> users = userService.getUsers(page, pageSize, userRole, banned, term, listId);

        if (users.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<UserDto> usersDto = UserDto.fromUserList(uriInfo, users.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserDto>>(usersDto) {
        });
        ResponseUtils.setPaginationLinks(response, users, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), users.getCurrentPage(), users.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{username}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        LOGGER.info("GET /{}: Returning user {}.", uriInfo.getPath(), user.getUsername());
        return Response.ok(UserDto.fromUser(uriInfo, user)).build();
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createUser(@Valid @NotEmptyBody UserCreateDto userDto) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        final User user = userService.register(userDto.getEmail(), userDto.getUsername(), userDto.getPassword(), userDto.getName());

        LOGGER.info("POST /{}: User {} created with id {}", uriInfo.getPath(), user.getUsername(), user.getUserId());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build()).build();
    }

    @DELETE
    @Path("/{username}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteUser(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.deleteUser(user);

        LOGGER.info("DELETE /{}: {} user deleted", uriInfo.getPath(), username);
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
                                @Valid @NotEmptyBody UserEditDto userEditDto) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.updateUserData(user, userEditDto.getName());

        LOGGER.info("PUT /{}: {} user updated", uriInfo.getPath(), username);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{username}/password")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response updatePassword(@PathParam("username") String username,
                                   @Valid @NotEmptyBody UserPasswordDto userPasswordDto) throws InvalidCurrentPasswordException {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.changePassword(user, userPasswordDto.getCurrentPassword(), userPasswordDto.getNewPassword());

        LOGGER.info("PUT /{}: {} user password updated", uriInfo.getPath(), username);
        return Response.noContent().build();
    }

    /**
     * Reset password - Recover
     */
    @POST
    @Path("/password-token")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createPasswordResetToken(@Valid @NotEmptyBody UserEmailDto userEmailDto) {
        final User user = userService.getByEmail(userEmailDto.getEmail()).orElseThrow(EmailNotFoundException::new);

        final Token token = userService.forgotPassword(user);

        LOGGER.info("POST /{}: Token created for {} with expiry date on {}", uriInfo.getPath(), user.getUsername(), token.getExpiryDate());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(token.getToken()).build()).build();
    }

    @PUT
    @Path("/password-token/{token}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response resetPassword(@PathParam("token") String tokenString,
                                  @Valid @NotEmptyBody UserResetPasswordDto userResetPasswordDto) throws InvalidTokenException {
        final Token token = tokenService.getToken(tokenString).orElseThrow(TokenNotFoundException::new);

        userService.resetPassword(token, userResetPasswordDto.getNewPassword());

        LOGGER.info("PUT /{}: Password reset for {}", uriInfo.getPath(), token.getUser().getUsername());
        return Response.noContent().build();
    }

    /**
     * User verification
     */
    @POST
    @Path("/verification-token")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response sendVerificationToken(@Valid @NotEmptyBody UserEmailDto userEmailDto) throws EmailAlreadyVerifiedException {
        final User user = userService.getByEmail(userEmailDto.getEmail()).orElseThrow(EmailNotFoundException::new);

        final Token token = userService.createVerificationToken(user);

        LOGGER.info("POST /{}: Token created for {} with expiry date on {}", uriInfo.getPath(), user.getUsername(), token.getExpiryDate());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(token.getToken()).build()).build();
    }

    @PUT
    @Path("/verification-token/{token}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response verifyUser(@PathParam("token") String tokenString) throws InvalidTokenException {
        final Token token = tokenService.getToken(tokenString).orElseThrow(TokenNotFoundException::new);

        final User user = userService.confirmRegister(token);

        LOGGER.info("PUT /{}: User {} enabled", uriInfo.getPath(), token.getUser().getUsername());
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

        LOGGER.info("GET /{}: Returning user {} image", uriInfo.getPath(), username);
        return Response.ok(profileImage).build();
    }

    @PUT
    @Path("/{username}/image")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    public Response updateProfileImage(@PathParam("username") String username,
                                       @Image @FormDataParam("image") final FormDataBodyPart image,
                                       @Size(max = 1024 * 1024 * 2) @FormDataParam("image") byte[] imageBytes) throws ImageConversionException {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.uploadUserProfileImage(user, imageBytes, image.getMediaType().getSubtype());

        LOGGER.info("PUT /{}: User {} image updated", uriInfo.getPath(), username);
        return Response.noContent().contentLocation(uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUsername())).path("image").build()).build();
    }

    @DELETE
    @Path("/{username}/image")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteProfileImage(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.deleteUserProfileImage(user);

        LOGGER.info("DELETE /{}: User {} profile image deleted", uriInfo.getPath(), username);
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

        LOGGER.info("POST /{}: Mod request added to user {}", uriInfo.getPath(), username);
        return Response.created(uriInfo.getBaseUriBuilder().path("mods-requests").path(String.valueOf(modRequest.getRequestId())).build()).build();
    }

    @DELETE
    @Path("{username}/mod")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response removeMod(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        moderatorService.removeMod(user);

        LOGGER.info("DELETE /{}: Mod role removed to user {}", uriInfo.getPath(), username);
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

        LOGGER.info("DELETE /{}: User {} banned", uriInfo.getPath(), username);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{username}/locked")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response unbanUser(@PathParam("username") String username) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        userService.unbanUser(user);

        LOGGER.info("DELETE /{}: User {} unbanned", uriInfo.getPath(), username);
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
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaFavoriteDto> mediaFavoriteDtoList = MediaFavoriteDto.fromMediaFavoriteList(uriInfo, favoriteMedia.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaFavoriteDto>>(mediaFavoriteDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, favoriteMedia, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), favoriteMedia.getCurrentPage(), favoriteMedia.getElements().size());
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
            LOGGER.info("GET /{}: media {} is not favorite of {}.", uriInfo.getPath(), mediaId, username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("GET /{}: media {} is favorite of {}.", uriInfo.getPath(), mediaId, username);
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

        LOGGER.info("PUT /{}: media {} added to {}'s favorites.", uriInfo.getPath(), mediaId, username);
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

        LOGGER.info("DELETE /{}: media {} removed from {}'s favorites.", uriInfo.getPath(), mediaId, username);
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
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaWatchedDto> mediaWatchedDtoList = MediaWatchedDto.fromWatchedMediaList(uriInfo, watchedMedia.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaWatchedDto>>(mediaWatchedDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, watchedMedia, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), watchedMedia.getCurrentPage(), watchedMedia.getElements().size());
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
            LOGGER.info("GET /{}: media {} is not watched by {}.", uriInfo.getPath(), mediaId, username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("GET /{}: media {} is watched by {} on {}.", uriInfo.getPath(), mediaId, username, watchedMedia.get().getWatchDate());
        return Response.ok(MediaWatchedDto.fromWatchedMediaAndUser(uriInfo, watchedMedia.get(), user)).build();
    }

    @PUT
    @Path("/{username}/watched-media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response addMediaToWatched(@PathParam("username") String username,
                                      @PathParam("mediaId") int mediaId,
                                      @Valid @NotEmptyBody DateTimeDto dateTimeDto) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        watchService.addWatchedMedia(media, user, dateTimeDto.getDateTime());

        LOGGER.info("PUT /{}: media {} added to {}'s watched on {}.", uriInfo.getPath(), mediaId, username, dateTimeDto.getDateTime().toLocalDate());
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

        LOGGER.info("DELETE /{}: media {} removed from {}'s watched.", uriInfo.getPath(), mediaId, username);
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
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaToWatchDto> mediaToWatchDtoList = MediaToWatchDto.fromMediaToWatchList(uriInfo, toWatchMedia.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaToWatchDto>>(mediaToWatchDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, toWatchMedia, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), toWatchMedia.getCurrentPage(), toWatchMedia.getElements().size());
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
            LOGGER.info("GET /{}: media {} is not to watch by {}.", uriInfo.getPath(), mediaId, username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("GET /{}: media {} is to watch by {}.", uriInfo.getPath(), mediaId, username);
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

        LOGGER.info("PUT /{}: media {} added to {}'s to watch.", uriInfo.getPath(), mediaId, username);
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

        LOGGER.info("DELETE /{}: media {} removed from {}'s to watch.", uriInfo.getPath(), mediaId, username);
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
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<MediaList> favoriteLists = favoriteService.getUserFavoriteLists(user, page, pageSize);

        return getFavoriteListsResponse(user, favoriteLists);
    }

    @GET
    @Path("/{username}/public-favorite-lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserPublicFavoriteLists(@PathParam("username") String username,
                                               @QueryParam("page") @DefaultValue(defaultPage) int page,
                                               @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<MediaList> publicFavoriteLists = favoriteService.getUserPublicFavoriteLists(user, page, pageSize);

        return getFavoriteListsResponse(user, publicFavoriteLists);
    }

    private Response getFavoriteListsResponse(User user, PageContainer<MediaList> lists) {
        if (lists.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<ListFavoriteDto> listFavoriteDtoList = ListFavoriteDto.fromListList(uriInfo, lists.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListFavoriteDto>>(listFavoriteDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, lists, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), lists.getCurrentPage(), lists.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{username}/favorite-lists/{listId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response isFavoriteList(@PathParam("username") String username,
                                   @PathParam("listId") int listId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        if (!favoriteService.isFavoriteList(mediaList, user)) {
            LOGGER.info("GET /{}: list {} is not favorite of {}.", uriInfo.getPath(), listId, username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("GET /{}: list {} is favorite of {}.", uriInfo.getPath(), listId, username);
        return Response.ok(ListFavoriteDto.fromList(uriInfo, mediaList, user)).build();
    }

    @PUT
    @Path("/{username}/favorite-lists/{listId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response addListToFavorites(@PathParam("username") String username,
                                       @PathParam("listId") int listId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        favoriteService.addListToFav(mediaList, user);

        LOGGER.info("PUT /{}: list {} added to {}'s favorites.", uriInfo.getPath(), listId, username);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{username}/favorite-lists/{listId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response removeListFromFavorites(@PathParam("username") String username,
                                            @PathParam("listId") int listId) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        favoriteService.deleteListFromFav(mediaList, user);

        LOGGER.info("DELETE /{}: list {} removed from {}'s favorites.", uriInfo.getPath(), listId, username);
        return Response.noContent().build();
    }

    /**
     * User Lists
     */
    @GET
    @Path("/{username}/lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserLists(@PathParam("username") String username,
                                 @QueryParam("page") @DefaultValue(defaultPage) int page,
                                 @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<MediaList> userLists = listsService.getMediaListByUser(user, page, pageSize);

        return getListsResponse(user, userLists);
    }

    @GET
    @Path("/{username}/public-lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserPublicLists(@PathParam("username") String username,
                                       @QueryParam("page") @DefaultValue(defaultPage) int page,
                                       @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<MediaList> userPublicLists = listsService.getPublicMediaListByUser(user, page, pageSize);

        return getListsResponse(user, userPublicLists);
    }

    @GET
    @Path("/{username}/editable-lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserEditableLists(@PathParam("username") String username,
                                         @QueryParam("page") @DefaultValue(defaultPage) int page,
                                         @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<MediaList> userEditableLists = listsService.getUserEditableLists(user, page, pageSize);

        return getListsResponse(user, userEditableLists);
    }

    private Response getListsResponse(User user, PageContainer<MediaList> lists) {
        if (lists.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<ListDto> listDtoList = ListDto.fromListList(uriInfo, lists.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListDto>>(listDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, lists, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), lists.getCurrentPage(), lists.getElements().size());
        return response.build();
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
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<NotificationDto> notificationDtoList = NotificationDto.fromNotificationList(uriInfo, notifications.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<NotificationDto>>(notificationDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, notifications, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), notifications.getCurrentPage(), notifications.getElements().size());
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
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final PageContainer<Request> collaborationRequests = collaborativeListService.getRequestsByUser(user, page, pageSize);

        if (collaborationRequests.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<CollaboratorRequestDto> collaboratorRequestDtoList = CollaboratorRequestDto.fromRequestList(uriInfo, collaborationRequests.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<CollaboratorRequestDto>>(collaboratorRequestDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, collaborationRequests, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), collaborationRequests.getCurrentPage(), collaborationRequests.getElements().size());
        return response.build();
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
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtoList = MediaDto.fromMediaList(uriInfo, recommendedMedia.getElements(), user);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, recommendedMedia, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), recommendedMedia.getCurrentPage(), recommendedMedia.getElements().size());
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

        return getListsResponse(user, recommendedLists);
    }
}
