package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.PasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
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


    private static final int listsPerPage = 4;
    private static final int itemsPerPage = 4;


    @RequestMapping("/user/{username}")
    public ModelAndView userProfile(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userProfile");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
//        List<MediaList> userLists = listsService.getMediaListByUserId(user.getUserId(), page - 1, listsPerPage);
        PageContainer<MediaList> userLists = listsService.getMediaListByUserId(user.getUserId(), page - 1, listsPerPage);
        final List<ListCover> userListsCover = getListCover(userLists.getElements(), listsService);
        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        mav.addObject(user);
        mav.addObject("lists", userListsCover);
        mav.addObject("userListsContainer", userLists);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        //
        final List<ListCover> userPublicListCover = getListCover(userLists.getElements(), listsService);
        PageContainer<MediaList> userPublicLists = listsService.getPublicMediaListByUserId(user.getUserId(), page - 1, listsPerPage);
        mav.addObject("userPublicListCover", userPublicListCover);
        mav.addObject("userPublicLists", userPublicLists);
        return mav;
    }

    @RequestMapping("/user/{username}/favoriteMedia")
    public ModelAndView userFavoriteMedia(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userFavoriteMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<Media> favoriteMedia = favoriteService.getUserFavoriteMedia(user.getUserId(), page - 1, itemsPerPage);
        PageContainer<Media> suggestedMedia = mediaService.getMediaList(page - 1, itemsPerPage);
        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        mav.addObject(user);
        mav.addObject("favoriteMediaContainer", favoriteMedia);
        mav.addObject("suggestedMediaContainer", suggestedMedia);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/favoriteMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

    @RequestMapping("/user/{username}/toWatchMedia")
    public ModelAndView userToWatchMedia(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userToWatchMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        int userId = user.getUserId();
        PageContainer<Media> toWatchMediaIds = watchService.getToWatchMediaId(userId, page - 1, itemsPerPage);
        // List<Media> toWatchMedia = toWatchMediaIds.getElements();
        PageContainer<Media> suggestedMedia = mediaService.getMediaList(page - 1, itemsPerPage);
        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        mav.addObject("title", "Watchlist");
        // mav.addObject("mediaList", toWatchMedia);
        mav.addObject(user);
        mav.addObject("toWatchMediaIdsContainer", toWatchMediaIds);
        mav.addObject("suggestedMediaContainer", suggestedMedia);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/toWatchMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }


    @RequestMapping("/user/{username}/watchedMedia")
    public ModelAndView userWatchedMedia(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userWatchedMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        int userId = user.getUserId();
        PageContainer<WatchedMedia> watchedMediaIds = watchService.getWatchedMediaId(userId, page - 1, itemsPerPage);
//        List<Media> watchedMedia = mediaService.getById(watchedMediaIds.getElements());
        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
//        mav.addObject("title", "Watched Media");
//        mav.addObject("mediaList", watchedMedia);
        mav.addObject(user);
        mav.addObject("watchedMediaIdsContainer", watchedMediaIds);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/watchedMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }


    @RequestMapping("/user/{username}/favoriteLists")
    public ModelAndView userFavoriteLists(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userFavoriteLists");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        mav.addObject(user);
//        List<Integer> userFavListsId = favoriteService.getUserFavoriteLists(user.getUserId(), page - 1, itemsPerPage);
        PageContainer<MediaList> userFavLists = favoriteService.getUserFavoriteLists(user.getUserId(), page - 1, itemsPerPage);
        List<ListCover> favoriteCovers = getListCover(userFavLists.getElements(), listsService);
        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        mav.addObject("favoriteLists", favoriteCovers);
        mav.addObject("userFavListsContainer", userFavLists);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/favoriteLists").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

    @RequestMapping(value = "/settings", method = {RequestMethod.GET})
    public ModelAndView editUserDetails(@ModelAttribute("userSettings") final UserForm form) {
        ModelAndView mav = new ModelAndView("userSettings");
        User u = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        mav.addObject("user", u);
        return mav;
    }

    @RequestMapping(value = "/settings", method = {RequestMethod.POST}, params = "submit")
    public ModelAndView postUserSettings(@Valid @ModelAttribute("userSettings") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return editUserDetails(form);
        return new ModelAndView("redirect:/user/" + form.getUsername());
    }

    @RequestMapping(value = "/changePassword", method = {RequestMethod.GET})
    public ModelAndView changeUserPassword(@ModelAttribute("changePassword") final PasswordForm form) {
        ModelAndView mav = new ModelAndView("changePassword");
        User u = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        mav.addObject("user", u);
        return mav;
    }

    @RequestMapping(value = "/changePassword", method = {RequestMethod.POST}, params = "submit, user")
    public ModelAndView postUserPassword(@Valid @ModelAttribute("changePassword") final PasswordForm form, final BindingResult errors, @RequestParam("user") final User user) {
        if (errors.hasErrors())
            return changeUserPassword(form);
        return new ModelAndView("redirect:/user/" + user.getUsername());
    }

    @RequestMapping(value = "/uploadImage", method = {RequestMethod.POST})
    public ModelAndView uploadProfilePicture(@RequestParam("username") final String username, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            try {
                byte[] photoBlob = IOUtils.toByteArray(file.getInputStream());
                Integer imageContentLength = Long.valueOf(file.getSize()).intValue();
                String imageContentType = file.getContentType();
                userService.getByUsername(username).ifPresent((user -> {
                    imageService.uploadUserProfilePicture(user.getUserId(), photoBlob, imageContentLength, imageContentType);
                }));
            } catch (Exception e) {
                return new ModelAndView("redirect:/user" + username).addObject("errorMsg", "You failed to upload" + "->" + e.getMessage());
            }
        }
        return new ModelAndView("redirect:/user/" + username);
    }

    @RequestMapping(value = "/getProfileImage")
    public ModelAndView getProfilePicture(@RequestParam("username") final String username) {
        userService.getByUsername(username).ifPresent((user -> {
            Image image = imageService.getUserProfilePicture(user.getUserId()).orElseThrow(RuntimeException::new);
        }));
        return new ModelAndView("redirect:/user/" + username);
    }
}
