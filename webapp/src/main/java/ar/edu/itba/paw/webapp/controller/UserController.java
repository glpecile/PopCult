package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private ListsService listsService;

    private static final int listsPerPage = 4;
    private static final int itemsPerPage = 12;


    @RequestMapping("/{username}")
    public ModelAndView userProfile(@PathVariable("username") final String username, @RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("userProfile");
        User user = userService.getCurrentUser();
        List<MediaList> userLists = listsService.getMediaListByUserId(user.getUserId(), page - 1, listsPerPage);
        final List<ListCover> userListsCover = getListCover(userLists, listsService, mediaService);
        mav.addObject(user);
        mav.addObject("lists", userListsCover);
        mav.addObject("listsPages", (int) Math.ceil((double) listsService.getListCountFromUserId(user.getUserId()).orElse(0) / listsPerPage));
        mav.addObject("currentPage", page);
        return mav;
    }

    @RequestMapping("/{username}/favoriteMedia")
    public ModelAndView userFavoriteMedia(@PathVariable("username") final String username,@RequestParam(value = "page", defaultValue = "1") final int page){
        ModelAndView mav = new ModelAndView("userFavoriteMedia");
        User user = userService.getCurrentUser();
        List<Media> userMedia = mediaService.getById(userService.getUserFavoriteMedia(page - 1, itemsPerPage));
        List<Media> suggestedMedia = mediaService.getMediaList(page-1, itemsPerPage);
        final Integer suggestedMediaCount = mediaService.getMediaCount().orElse(0);
        Integer mediaCount = userService.getFavoriteMediaCount().orElse(0);
        mav.addObject("mediaList",userMedia);
        mav.addObject("favoriteAmount", mediaCount);
        mav.addObject("suggestedMedia", suggestedMedia);
        mav.addObject("suggestedMediaPages",(int) Math.ceil((double) suggestedMediaCount / itemsPerPage));
        mav.addObject("mediaPages", (int) Math.ceil((double) mediaCount / itemsPerPage));
        mav.addObject("currentPage", page);
        mav.addObject(user);
        return mav;
    }

    @RequestMapping("/{username}/favoriteLists")
    public ModelAndView userFavoriteLists(@PathVariable("username") final String username,@RequestParam(value = "page", defaultValue = "1") final int page){
        ModelAndView mav = new ModelAndView("userFavoriteLists");
        User user = userService.getCurrentUser();
        mav.addObject(user);
        List<Integer> userFavListsId = userService.getUserFavoriteLists(page-1, itemsPerPage);
        List<ListCover> favoriteCovers = getListCover(listsService.getMediaListById(userFavListsId), listsService, mediaService);
        Integer favCount = userService.getFavoriteMediaCount().orElse(0);
        mav.addObject("favoriteLists", favoriteCovers);
        mav.addObject("currentPage", page);
        mav.addObject("listsPages",(int) Math.ceil((double) favCount / itemsPerPage));
        mav.addObject("listsAmount", favCount);
        return mav;
    }
}
