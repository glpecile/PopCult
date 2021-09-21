package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ImageForm;
import ar.edu.itba.paw.webapp.form.PasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
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
    public ModelAndView userProfile(@ModelAttribute("imageForm") final ImageForm imageForm,
                                    @PathVariable("username") final String username,
                                    @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userProfile");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
//        List<MediaList> userLists = listsService.getMediaListByUserId(user.getUserId(), page - 1, listsPerPage);
        PageContainer<MediaList> userLists = listsService.getMediaListByUserId(user.getUserId(), page - 1, listsPerPage);
        final List<ListCover> userListsCover = getListCover(userLists.getElements(), listsService);
        mav.addObject("user", user);
        mav.addObject("lists", userListsCover);
        mav.addObject("userListsContainer", userLists);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
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
    public ModelAndView userFavoriteMedia(@ModelAttribute("imageForm") final ImageForm imageForm,
                                          @PathVariable("username") final String username,
                                          @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userFavoriteMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<Media> favoriteMedia = favoriteService.getUserFavoriteMedia(user.getUserId(), page - 1, itemsPerPage);
        PageContainer<Media> suggestedMedia = mediaService.getMediaList(page - 1, itemsPerPage);
        mav.addObject("user", user);
        mav.addObject("favoriteMediaContainer", favoriteMedia);
        mav.addObject("suggestedMediaContainer", suggestedMedia);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/favoriteMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

    @RequestMapping("/user/{username}/toWatchMedia")
    public ModelAndView userToWatchMedia(@ModelAttribute("imageForm") final ImageForm imageForm,
                                         @PathVariable("username") final String username,
                                         @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userToWatchMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<Media> toWatchMediaIds = watchService.getToWatchMediaId(user.getUserId(), page - 1, itemsPerPage);
        PageContainer<Media> suggestedMedia = mediaService.getMediaList(page - 1, itemsPerPage);
        // List<Media> toWatchMedia = toWatchMediaIds.getElements();
//        mav.addObject("title", "Watchlist");
        // mav.addObject("mediaList", toWatchMedia);
        mav.addObject("user", user);
        mav.addObject("toWatchMediaIdsContainer", toWatchMediaIds);
        mav.addObject("suggestedMediaContainer", suggestedMedia);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/toWatchMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }


    @RequestMapping("/user/{username}/watchedMedia")
    public ModelAndView userWatchedMedia(@ModelAttribute("imageForm") final ImageForm imageForm,
                                         @PathVariable("username") final String username,
                                         @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userWatchedMedia");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        PageContainer<WatchedMedia> watchedMediaIds = watchService.getWatchedMediaId(user.getUserId(), page - 1, itemsPerPage);
//        List<Media> watchedMedia = mediaService.getById(watchedMediaIds.getElements());
//        mav.addObject("mediaList", watchedMedia);
        mav.addObject("user", user);
        mav.addObject("watchedMediaIdsContainer", watchedMediaIds);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/watchedMedia").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }


    @RequestMapping("/user/{username}/favoriteLists")
    public ModelAndView userFavoriteLists(@ModelAttribute("imageForm") final ImageForm imageForm,
                                          @PathVariable("username") final String username,
                                          @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userFavoriteLists");
        User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        mav.addObject(user);
//        List<Integer> userFavListsId = favoriteService.getUserFavoriteLists(user.getUserId(), page - 1, itemsPerPage);
        PageContainer<MediaList> userFavLists = favoriteService.getUserFavoriteLists(user.getUserId(), page - 1, itemsPerPage);
        List<ListCover> favoriteCovers = getListCover(userFavLists.getElements(), listsService);
        mav.addObject("favoriteLists", favoriteCovers);
        mav.addObject("userFavListsContainer", userFavLists);

        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        String urlBase = UriComponentsBuilder.newInstance().path("/user/{username}/favoriteLists").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

    @RequestMapping(value = "/settings", method = {RequestMethod.GET})
    public ModelAndView editUserDetails(@ModelAttribute("userSettings") final UserForm form) {
        ModelAndView mav = new ModelAndView("userSettings");
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        mav.addObject("user", user);
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
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/changePassword", method = {RequestMethod.POST}, params = "submit, user")
    public ModelAndView postUserPassword(@Valid @ModelAttribute("changePassword") final PasswordForm form, final BindingResult errors, @RequestParam("user") final User user) {
        if (errors.hasErrors())
            return changeUserPassword(form);
        return new ModelAndView("redirect:/user/" + user.getUsername());
    }

    @RequestMapping(value = "/uploadImage", method = {RequestMethod.POST})//TODO cambiar path porque es muy general
    public ModelAndView uploadProfilePicture(@Valid @ModelAttribute("imageForm") final ImageForm imageForm,
                                             final BindingResult error) throws IOException {
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if (error.hasErrors()) {
            return userProfile(imageForm, user.getUsername(), 1);
        }

        userService.uploadUserProfileImage(user.getUserId(), imageForm.getImage().getBytes(), imageForm.getImage().getSize(), imageForm.getImage().getContentType());
        return new ModelAndView("redirect:/user/" + user.getUsername());
    }

    @RequestMapping(value = "/user/image/{imageId}", method = RequestMethod.GET, produces = "image/*")
    public @ResponseBody
    byte[] getProfilePicture(@PathVariable("imageId") final int imageId) {
        return userService.getUserProfileImage(imageId).orElseThrow(ImageNotFoundException::new).getImageBlob();
    }

    @RequestMapping(value = "/editWatchedDate", method = {RequestMethod.POST}, params = "watchedDate")
    public ModelAndView editWatchedDate(@RequestParam("username") final String username, @RequestParam("watchedDate") String watchedDate) {
        System.out.println(watchedDate);
        return new ModelAndView("redirect:/user/" + username + "/watchedMedia");
    }
}
