package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ListForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;


@Controller
public class ListsController {
    @Autowired
    private UserService userService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private ListsService listsService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private SearchService searchService;

    private static final int itemsPerPage = 4;
    private static final int discoveryListsAmount = 4;
    private static final int lastAddedAmount = 4;
    private static final int defaultValue = 1;

    @RequestMapping("/lists")
    public ModelAndView lists(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("lists");
        final PageContainer<MediaList> allLists = listsService.getAllLists(page - 1, itemsPerPage);
        final List<ListCover> discoveryCovers = generateCoverList(listsService.getDiscoveryMediaLists(discoveryListsAmount));
        final List<ListCover> recentlyAddedCovers = generateCoverList(listsService.getNLastAddedList(lastAddedAmount));
        final List<ListCover> allListsCovers = generateCoverList(allLists.getElements());
        mav.addObject("discovery", discoveryCovers);
        mav.addObject("recentlyAdded", recentlyAddedCovers);
        mav.addObject("allLists", allListsCovers);
        mav.addObject("allListContainer", allLists);
        return mav;
    }

    private List<ListCover> generateCoverList(List<MediaList> MediaListLists) {
        return getListCover(MediaListLists, listsService);
    }

    @RequestMapping("/lists/{listId}")
    public ModelAndView listDescription(@PathVariable("listId") final int listId) {
        final ModelAndView mav = new ModelAndView("listDescription");
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User u = listsService.getListOwner(mediaList.getMediaListId()).orElseThrow(UserNotFoundException::new);
        final List<Media> mediaFromList = listsService.getMediaIdInList(listId);
        mav.addObject("list", mediaList);
        mav.addObject("media", mediaFromList);
        mav.addObject("user", u);
        userService.getCurrentUser().ifPresent(user -> {
            mav.addObject("currentUser", user);
            mav.addObject("isFavoriteList", favoriteService.isFavoriteList(listId, user.getUserId()));
        });
        return mav;
    }

    //CREATE A NEW LIST - PART 1
    @RequestMapping(value = "/createList", method = {RequestMethod.GET})
    public ModelAndView createListForm(@ModelAttribute("createListForm") final ListForm form) {
        return new ModelAndView("createListForm");
    }

    @RequestMapping(value = "/createList", method = {RequestMethod.POST})
    public ModelAndView postListForm(@Valid @ModelAttribute("createListForm") final ListForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return createListForm(form);
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        final MediaList mediaList = listsService.createMediaList(user.getUserId(), form.getListTitle(), form.getDescription(), form.isVisible(), form.isCollaborative());
        return addMediaToList(defaultValue, mediaList.getMediaListId(), null, null, null);
    }

    //CREATE A NEW LIST - PART 2
    @RequestMapping(value = "/addMedia/{listId}", method = {RequestMethod.GET})
    public ModelAndView addMediaToList(@RequestParam(value = "page", defaultValue = "1") final int page, @PathVariable("listId") Integer mediaListId, @RequestParam(required = false) String searchTerm, @RequestParam(required = false) PageContainer<Media> searchFilmsResults, @RequestParam(required = false) PageContainer<Media> searchSeriesResults) {
        final ModelAndView mav = new ModelAndView("addMediaToList");
        return addMediaObjects(page, mediaListId, searchTerm, searchFilmsResults, searchSeriesResults, mav);
    }

    @RequestMapping(value = "/addMedia/{listId}", method = {RequestMethod.DELETE, RequestMethod.POST})
    public ModelAndView deleteFromList(@RequestParam(value = "page", defaultValue = "1") final int page, @PathVariable("listId") Integer mediaListId, @RequestParam("mediaId") Integer mediaId) {
        listsService.deleteMediaFromList(mediaListId, mediaId);
        return addMediaToList(page, mediaListId, null, null, null);
    }

    @RequestMapping(value = "/addMedia/{listId}", method = {RequestMethod.GET}, params = "search")
    public ModelAndView searchMediaToAddToList(@RequestParam(value = "page", defaultValue = "1") final int page,
                                               @PathVariable("listId") Integer mediaListId, HttpServletRequest request,
                                               @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                               final BindingResult errors,
                                               @RequestParam(value = "sort", defaultValue = "title") final String sortType) {

        if (errors.hasErrors()) {
//            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final PageContainer<Media> searchFilmsResults = searchService.searchMediaByTitleNotInList(mediaListId, searchForm.getTerm(), page - 1, itemsPerPage, MediaType.MOVIE.ordinal(), SortType.valueOf(sortType.toUpperCase()).ordinal());
        final PageContainer<Media> searchSeriesResults = searchService.searchMediaByTitleNotInList(mediaListId, searchForm.getTerm(), page - 1, itemsPerPage, MediaType.SERIE.ordinal(), SortType.valueOf(sortType.toUpperCase()).ordinal());
        return addMediaToList(page, mediaListId, searchForm.getTerm(), searchFilmsResults, searchSeriesResults);
    }

    @RequestMapping(value = "/addMedia/{listId}", method = {RequestMethod.POST}, params = "add")
    public ModelAndView insertToList(@RequestParam(value = "page", defaultValue = "1") final int page, @PathVariable("listId") Integer mediaListId, @RequestParam("mediaId") Integer selectedMedia) {
        try {
            listsService.addToMediaList(mediaListId, selectedMedia);
        } catch (MediaAlreadyInListException e) {
            return addMediaToList(page, mediaListId, null, null, null).addObject("alreadyInList", true);//TODO add in jsp message.
        }
        return addMediaToList(page, mediaListId, null, null, null);
    }
    //END CREATE A NEW LIST

    //EDIT A LIST - PART 1
    @RequestMapping(value = "/editList/{listId}", method = {RequestMethod.GET})
    public ModelAndView editList(@PathVariable("listId") final int listId, @ModelAttribute("createListForm") final ListForm form) {
        final ModelAndView mav = new ModelAndView("editList");
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final List<Media> mediaFromList = listsService.getMediaIdInList(listId);
        mav.addObject("list", mediaList);
        mav.addObject("media", mediaFromList);
        return mav;
    }

    @RequestMapping(value = "/editList/{listId}", method = {RequestMethod.DELETE, RequestMethod.POST}, params = "delete")
    public ModelAndView deleteList(@PathVariable("listId") final int listId) {
        listsService.deleteList(listId);
        return new ModelAndView("redirect:/lists");
    }

    @RequestMapping(value = "/editList/{listId}", method = {RequestMethod.POST}, params = "save")
    public ModelAndView submitList(@PathVariable("listId") final int listId, @Valid @ModelAttribute("createListForm") final ListForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return editList(listId, form);
        listsService.updateList(listId, form.getListTitle(), form.getDescription(), form.isVisible(), form.isCollaborative());
        //update stuff
        return addMediaToList(defaultValue, listId, null, null, null);
    }
    //END EDIT LIST

    @RequestMapping(value = "/lists/{listId}", method = {RequestMethod.POST}, params = "fork")
    public ModelAndView createListCopy(@PathVariable("listId") final int listId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        final MediaList newList = listsService.createMediaListCopy(user.getUserId(), listId).orElseThrow(ListNotFoundException::new);
        return new ModelAndView("redirect:/lists/" + newList.getMediaListId());
    }

    @RequestMapping(value = "/lists/{listId}", method = {RequestMethod.POST}, params = "addFav")
    public ModelAndView addListToFav(@PathVariable("listId") final int listId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        favoriteService.addListToFav(listId, user.getUserId());
        return listDescription(listId);
    }

    @RequestMapping(value = "/lists/{listId}", method = {RequestMethod.POST}, params = "deleteFav")
    public ModelAndView deleteListFromFav(@PathVariable("listId") final int listId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        favoriteService.deleteListFromFav(listId, user.getUserId());
        return listDescription(listId);
    }

    private ModelAndView addMediaObjects(@RequestParam(value = "page", defaultValue = "1") int page, @PathVariable("listId") Integer mediaListId, @RequestParam(required = false) String searchTerm, @RequestParam(required = false) PageContainer<Media> searchFilmsResults, @RequestParam(required = false) PageContainer<Media> searchSeriesResults, ModelAndView mav) {
        PageContainer<Media> pageContainer = listsService.getMediaIdInList(mediaListId, page - 1, itemsPerPage);
        mav.addObject("mediaContainer", pageContainer);
        mav.addObject("mediaListId", mediaListId);
        mav.addObject("searchTerm", searchTerm);
        mav.addObject("searchFilmsContainer", searchFilmsResults);
        mav.addObject("searchSeriesContainer", searchSeriesResults);
        return mav;
    }
}
