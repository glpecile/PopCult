package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.EmailNotExistsException;
import ar.edu.itba.paw.interfaces.exceptions.ImageConversionException;
import ar.edu.itba.paw.interfaces.exceptions.InvalidCurrentPasswordException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.TokenNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;


@Controller
public class UserController {    
    @Autowired
    private ImageService imageService;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final int listsPerPage = 4;
    private static final int itemsPerPage = 4;
    private static final int notificationsPerPage = 16;
    private static final int editablePerPage = 6;


    @RequestMapping("/user/{username}")
    public ModelAndView userProfile(@ModelAttribute("imageForm") final ImageForm imageForm,
                                    @PathVariable("username") final String username,
                                    @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userProfile");
        LOGGER.debug("Trying to access {} profile", username);
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<MediaList> userLists = listsService.getMediaListByUser(user, page - 1, listsPerPage);
        final List<ListCover> userListsCover = getListCover(userLists.getElements(), listsService);
        mav.addObject("user", user);
        mav.addObject("lists", userListsCover);
        mav.addObject("userListsContainer", userLists);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);

        PageContainer<MediaList> userPublicLists = listsService.getPublicMediaListByUser(user, page - 1, listsPerPage);
        final List<ListCover> userPublicListCover = getListCover(userPublicLists.getElements(), listsService);
        mav.addObject("userPublicListCover", userPublicListCover);
        mav.addObject("userPublicLists", userPublicLists);
        LOGGER.info("{} profile accessed.", username);
        return mav;
    }

    @RequestMapping("/user/{username}/favoriteMedia")
    public ModelAndView userFavoriteMedia(@ModelAttribute("imageForm") final ImageForm imageForm,
                                          @PathVariable("username") final String username,
                                          @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access {} favorite media.", username);
        ModelAndView mav = new ModelAndView("userFavoriteMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<Media> favoriteMedia = favoriteService.getUserFavoriteMedia(user.getUserId(), page - 1, itemsPerPage);
        PageContainer<Media> suggestedMedia = favoriteService.getMostLikedMedia(page - 1, itemsPerPage);
        mav.addObject("user", user);
        mav.addObject("favoriteMediaContainer", favoriteMedia);
        mav.addObject("suggestedMediaContainer", suggestedMedia);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/favoriteMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);

        LOGGER.info("{} favorite media accessed.", username);
        return mav;
    }

    @RequestMapping("/user/{username}/toWatchMedia")
    public ModelAndView userToWatchMedia(@ModelAttribute("imageForm") final ImageForm imageForm,
                                         @PathVariable("username") final String username,
                                         @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access {} to watch media.", username);
        ModelAndView mav = new ModelAndView("userToWatchMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<Media> toWatchMediaIds = watchService.getToWatchMediaId(user.getUserId(), page - 1, itemsPerPage);
        PageContainer<Media> suggestedMedia = favoriteService.getMostLikedMedia(page - 1, itemsPerPage);

        mav.addObject("user", user);
        mav.addObject("toWatchMediaIdsContainer", toWatchMediaIds);
        mav.addObject("suggestedMediaContainer", suggestedMedia);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/toWatchMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        LOGGER.info("{} to watch media accessed.", username);
        return mav;
    }


    @RequestMapping("/user/{username}/watchedMedia")
    public ModelAndView userWatchedMedia(@ModelAttribute("imageForm") final ImageForm imageForm,
                                         @PathVariable("username") final String username,
                                         @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access {} watched media.", username);
        ModelAndView mav = new ModelAndView("userWatchedMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<WatchedMedia> watchedMediaIds = watchService.getWatchedMediaId(user.getUserId(), page - 1, itemsPerPage);

        mav.addObject("user", user);
        mav.addObject("watchedMediaIdsContainer", watchedMediaIds);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/watchedMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);

        LOGGER.info("{} watched media accessed.", username);
        return mav;
    }


    @RequestMapping("/user/{username}/favoriteLists")
    public ModelAndView userFavoriteLists(@ModelAttribute("imageForm") final ImageForm imageForm,
                                          @PathVariable("username") final String username,
                                          @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access {} favorite lists.", username);
        ModelAndView mav = new ModelAndView("userFavoriteLists");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        mav.addObject(user);

        PageContainer<MediaList> userFavLists = favoriteService.getUserFavoriteLists(user.getUserId(), page - 1, itemsPerPage);
        List<ListCover> favoriteCovers = getListCover(userFavLists.getElements(), listsService);
        mav.addObject("favoriteLists", favoriteCovers);
        mav.addObject("userFavListsContainer", userFavLists);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/favoriteLists").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);

        PageContainer<MediaList> userPublicFavLists = favoriteService.getUserPublicFavoriteLists(user.getUserId(), page - 1, listsPerPage);
        final List<ListCover> userPublicFavListCover = getListCover(userPublicFavLists.getElements(), listsService);
        mav.addObject("userPublicListCover", userPublicFavListCover);
        mav.addObject("userPublicLists", userPublicFavLists);

        LOGGER.info("{} favorite lists accessed.", username);
        return mav;
    }

    @RequestMapping(value = "/settings", method = {RequestMethod.GET})
    public ModelAndView editUserDetails(@ModelAttribute("userSettings") final UserDataForm form) {
        ModelAndView mav = new ModelAndView("userSettings");
        LOGGER.debug("Trying to access {} details.", form.getUsername());
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        LOGGER.info("{} getting details.", user.getUsername());
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/settings", method = {RequestMethod.POST}, params = "editUser")
    public ModelAndView postUserSettings(@Valid @ModelAttribute("userSettings") final UserDataForm form, final BindingResult errors) {
        LOGGER.debug("{} trying to update settings", form.getUsername());
        if (errors.hasErrors()) {
            LOGGER.error("Form used for user details has errors.");
            return editUserDetails(form);
        }
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        userService.updateUserData(user, form.getName());
        LOGGER.info("User {}'s details updated.", user.getUsername());
        return new ModelAndView("redirect:/user/" + user.getUsername());
    }

    @RequestMapping(value = "/changePassword", method = {RequestMethod.GET})
    public ModelAndView changeUserPassword(@ModelAttribute("changePassword") final PasswordForm form) {
        ModelAndView mav = new ModelAndView("changePassword");
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/changePassword", method = {RequestMethod.POST}, params = "changePass")
    public ModelAndView postUserPassword(@Valid @ModelAttribute("changePassword") final PasswordForm form, final BindingResult errors) {
        LOGGER.debug("Trying to change password");
        if (errors.hasErrors()) {
            LOGGER.error("Change password form has errors.");
            return changeUserPassword(form);
        }
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        try {
            userService.changePassword(user, form.getCurrentPassword(), form.getNewPassword());
        } catch (InvalidCurrentPasswordException e) {
            LOGGER.error("Changing password failed.");
            errors.rejectValue("currentPassword", "validation.email.wrongCurrentPassword");
            return changeUserPassword(form);
        }
        LOGGER.info("{} changed password.", user.getUsername());
        return new ModelAndView("redirect:/user/" + user.getUsername());
    }

    @RequestMapping(value = "/forgotPassword", method = {RequestMethod.GET})
    public ModelAndView forgotPasswordForm(@ModelAttribute("emailForm") final EmailForm emailForm) {
        return new ModelAndView("forgotPassword");
    }

    @RequestMapping(value = "/forgotPassword", method = {RequestMethod.POST})
    public ModelAndView forgotPassword(@Valid @ModelAttribute("emailForm") final EmailForm emailForm,
                                       final BindingResult errors) {
        LOGGER.debug("{} initializing process to recover password.", emailForm.getEmail());
        if (errors.hasErrors()) {
            LOGGER.error("Email form has errors.");
            return forgotPasswordForm(emailForm);
        }
        try {
            userService.forgotPassword(emailForm.getEmail());
        } catch (EmailNotExistsException e) {
            LOGGER.info("{} does not exist. Recovery failed.", emailForm.getEmail());
            errors.rejectValue("email", "forgotPassword.emailNotExists");
            return forgotPasswordForm(emailForm);
        }
        LOGGER.info("Forgot password email sent to {}.", emailForm.getEmail());
        return new ModelAndView("sentEmail");
    }

    @RequestMapping(value = "resetPassword", method = {RequestMethod.GET})
    public ModelAndView resetPasswordForm(@ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm,
                                          @RequestParam(value = "token", defaultValue = "") final String token) {
        return new ModelAndView("resetPassword");
    }

    @RequestMapping(value = "resetPassword", method = {RequestMethod.POST})
    public ModelAndView resetPassword(@Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm,
                                      final BindingResult errors,
                                      @RequestParam(value = "token", defaultValue = "") final String token) {
        LOGGER.debug("Trying to rest password.");
        if (errors.hasErrors()) {
            LOGGER.error("Reset password form has errors.");
            return resetPasswordForm(resetPasswordForm, token);
        }
        Token resetPasswordToken = tokenService.getToken(token).orElseThrow(TokenNotFoundException::new);
        if (userService.resetPassword(resetPasswordToken, resetPasswordForm.getNewPassword())) {
            LOGGER.info("Password reset was successful.");
            return new ModelAndView("redirect:/login");
        }
        LOGGER.info("Token timed out.");
        return new ModelAndView("redirect:/tokenTimedOut?token=" + token);
    }


    @RequestMapping(value = "/user/{username}", method = {RequestMethod.POST}, params = "uploadImage")
    public ModelAndView uploadProfilePicture(@PathVariable("username") final String username, @Valid @ModelAttribute("imageForm") final ImageForm imageForm,
                                             final BindingResult error) throws IOException {
        LOGGER.debug("{} trying to upload profile picture", username);
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        if (error.hasErrors()) {
            LOGGER.error("Uploading profile picture failed.");
            return userProfile(imageForm, username, 1).addObject("errorUploadingImage", true);
        }
        userService.uploadUserProfileImage(user, imageForm.getImage().getBytes());
        LOGGER.info("{} profile picture uploaded successfully", username);
        return new ModelAndView("redirect:/user/" + username);
    }

    @RequestMapping(value = "/user/image/{imageId}", method = RequestMethod.GET, produces = "image/*")
    public @ResponseBody
    byte[] getProfileImage(@PathVariable("imageId") final Integer imageId) {
        LOGGER.debug("Trying to access profile image");
        byte[] profileImage = new byte[0];
        try {
            profileImage = userService.getUserProfileImage(imageId).orElseThrow(ImageNotFoundException::new).getImageBlob();
        } catch (ImageConversionException e) {
            LOGGER.error("Error loading image {}", imageId);
        }
        LOGGER.info("Profile image accessed.");
        return profileImage;
    }

    @RequestMapping(value = "/user/{username}/watchedMedia", method = {RequestMethod.POST}, params = "watchedDate")
    public ModelAndView editWatchedDate(@PathVariable("username") final String username, @RequestParam("watchedDate") String watchedDate, @RequestParam("userId") int userId, @RequestParam("mediaId") int mediaId) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        LOGGER.debug("{} is trying to edit watch date", username);
        watchService.updateWatchedMediaDate(mediaId, userId, f.parse(watchedDate));
        LOGGER.info("{} updated successfully watched date", username);
        return new ModelAndView("redirect:/user/" + username + "/watchedMedia");
    }

    @RequestMapping("/user/{username}/requests")
    public ModelAndView userCollabRequests(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("{} trying to access collaborations requests", username);
        ModelAndView mav = new ModelAndView("userRequests");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<Request> requestContainer = collaborativeListService.getRequestsByUserId(user.getUserId(), page - 1, itemsPerPage * 4);
        mav.addObject("username", username);
        mav.addObject("requestContainer", requestContainer);
        LOGGER.info("{} accessed succesfully to collaborations requests", username);
        return mav;
    }

    @RequestMapping("/user/{username}/requests/accept")
    public ModelAndView acceptCollabRequests(@PathVariable("username") final String username, @RequestParam("collabId") final int collabId) {
        LOGGER.debug("{} trying to accept collab request", username);
        collaborativeListService.acceptRequest(collabId);
        LOGGER.info("{} collab request accepted", username);
        return new ModelAndView("redirect:/user/" + username + "/requests");
    }

    @RequestMapping("/user/{username}/requests/reject")
    public ModelAndView rejectCollabRequests(@PathVariable("username") final String username, @RequestParam("collabId") final int collabId) {
        LOGGER.debug("{} trying to reject collab request", username);
        collaborativeListService.rejectRequest(collabId);
        LOGGER.info("{} collab request rejected", username);
        return new ModelAndView("redirect:/user/" + username + "/requests");
    }

    @RequestMapping("user/{username}/lists")
    public ModelAndView userEditableLists(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("{} trying to access editable lists", username);
        ModelAndView mav = new ModelAndView("userEditableLists");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<MediaList> editableLists = listsService.getUserEditableLists(user, page - 1, editablePerPage);
        final List<ListCover> editableCovers = getListCover(editableLists.getElements(), listsService);
        mav.addObject("user", user);
        mav.addObject("listContainer", editableLists);
        mav.addObject("covers", editableCovers);
        LOGGER.info("{} accessed editable lists", username);
        return mav;
    }

    @RequestMapping("user/{username}/notifications")
    public ModelAndView userNotifications(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("{} trying to access notifications", username);
        ModelAndView mav = new ModelAndView("userNotifications");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<ListComment> notificationContainer = commentService.getUserListsCommentsNotifications(user, page - 1, notificationsPerPage);
        mav.addObject("username", username);
        mav.addObject("notifications", notificationContainer);
        LOGGER.info("{} accessed notifications", username);
        return mav;
    }

    @RequestMapping(value = "user/{username}/notifications", method = {RequestMethod.POST}, params = "setOpen")
    public ModelAndView setNotificationsAsOpen(@PathVariable("username") final String username) {
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        commentService.setUserListsCommentsNotificationsAsOpened(user);
        return new ModelAndView("redirect:/user/" + username + "/notifications");
    }

    @RequestMapping(value = "user/{username}/notifications", method = {RequestMethod.POST}, params = "deleteNotifications")
    public ModelAndView deleteAllNotifications(@PathVariable("username") final String username) {
        LOGGER.debug("{} trying to delete all notifications", username);
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        commentService.deleteUserListsCommentsNotifications(user);
        LOGGER.info("{} deleted all notifications successfully ", username);
        return new ModelAndView("redirect:/user/" + username + "/notifications");
    }
}
