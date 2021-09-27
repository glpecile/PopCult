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
        final List<ListCover> mostLikedLists = generateCoverList(listsService.getMostLikedLists(defaultValue - 1, lastAddedAmount).getElements());
        final List<ListCover> allListsCovers = generateCoverList(allLists.getElements());
        mav.addObject("discovery", discoveryCovers);
        mav.addObject("mostLikedLists", mostLikedLists);
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

    //CREATE A NEW LIST
    @RequestMapping(value = "/lists/new", method = {RequestMethod.GET})
    public ModelAndView createListForm(@ModelAttribute("createListForm") final ListForm form) {
        return new ModelAndView("createListForm");
    }

    @RequestMapping(value = "/lists/new", method = {RequestMethod.POST})
    public ModelAndView postListForm(@Valid @ModelAttribute("createListForm") final ListForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return createListForm(form);
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        final MediaList mediaList = listsService.createMediaList(user.getUserId(), form.getListTitle(), form.getDescription(), form.isVisible(), form.isCollaborative());
        return manageMediaFromList(mediaList.getMediaListId(), null, null, form);
    }

    // MANAGE MEDIA IN LIST
    @RequestMapping(value = "/lists/edit/{listId}/manageMedia", method = {RequestMethod.GET})
    public ModelAndView manageMediaFromList(@PathVariable("listId") Integer mediaListId, @RequestParam(required = false) String searchTerm, @RequestParam(required = false) List<Media> searchResults, @ModelAttribute("editListDetails") final ListForm form) {
        final ModelAndView mav = new ModelAndView("manageMediaFromList");
        return addMediaObjects(mediaListId, searchTerm, searchResults, mav);
    }

    @RequestMapping(value = "/lists/edit/{listId}/deleteMedia", method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
    public ModelAndView deleteFromList(@PathVariable("listId") Integer mediaListId, @RequestParam("mediaId") Integer mediaId, @ModelAttribute("editListDetails") final ListForm form) {
        listsService.deleteMediaFromList(mediaListId, mediaId);
        return manageMediaFromList(mediaListId, null, null, form);
    }

    @RequestMapping(value = "/lists/edit/{listId}/search", method = {RequestMethod.GET}, params = "search")
    public ModelAndView searchMediaToAddToList(@PathVariable("listId") Integer mediaListId, HttpServletRequest request,
                                               @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                               final BindingResult errors,
                                               @RequestParam(value = "sort", defaultValue = "title") final String sortType,
                                               @ModelAttribute("editListDetails") final ListForm form) {

        if (errors.hasErrors()) {
//            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            // return new ModelAndView("redirect: " + request.getHeader("referer"));
            return manageMediaFromList(mediaListId, null, null, form);
        }
        final List<Media> searchResults = searchService.searchMediaByTitleNotInList(mediaListId, searchForm.getTerm(), defaultValue - 1 , itemsPerPage, MediaType.MOVIE.ordinal(), SortType.valueOf(sortType.toUpperCase()).ordinal()).getElements();
        searchResults.addAll (searchService.searchMediaByTitleNotInList(mediaListId, searchForm.getTerm(), defaultValue - 1, itemsPerPage, MediaType.SERIE.ordinal(), SortType.valueOf(sortType.toUpperCase()).ordinal()).getElements());
        return manageMediaFromList(mediaListId, searchForm.getTerm(), searchResults, form);
    }

    @RequestMapping(value = "/lists/edit/{listId}/addMedia", method = {RequestMethod.POST}, params = "add")
    public ModelAndView insertToList(@PathVariable("listId") Integer mediaListId, @RequestParam("mediaId") Integer selectedMedia, @ModelAttribute("editListDetails") final ListForm form) {
        try {
            listsService.addToMediaList(mediaListId, selectedMedia);
        } catch (MediaAlreadyInListException e) {
            return manageMediaFromList(mediaListId, null, null, form).addObject("alreadyInList", true);//TODO add in jsp message.
        }
        return manageMediaFromList(mediaListId, null, null, form);
    }

    @RequestMapping(value = "/lists/edit/{listId}/delete", method = {RequestMethod.DELETE, RequestMethod.POST}, params = "delete")
    public ModelAndView deleteList(@PathVariable("listId") final int listId) {
        listsService.deleteList(listId);
        return new ModelAndView("redirect:/lists");
    }

    @RequestMapping(value = "/lists/edit/{listId}/update", method = {RequestMethod.POST}, params = "save")
    public ModelAndView submitList(@PathVariable("listId") final int listId, @Valid @ModelAttribute("editListDetails") final ListForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            System.out.println(errors.hasErrors());
            return manageMediaFromList(listId, null, null, form).addObject("editDetailsErrors", errors.hasErrors());
//            return manageMediaFromList(page, listId, null, null, form)
            }
        listsService.updateList(listId, form.getListTitle(), form.getDescription(), form.isVisible(), form.isCollaborative());
        //update stuff
        return manageMediaFromList(listId, null, null, form);
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

    private ModelAndView addMediaObjects(@PathVariable("listId") Integer mediaListId, @RequestParam(required = false) String searchTerm, @RequestParam(required = false) List<Media> searchResults, ModelAndView mav) {
        PageContainer<Media> pageContainer = listsService.getMediaIdInList(mediaListId, defaultValue - 1, itemsPerPage);
        mav.addObject("list", listsService.getMediaListById(mediaListId).orElseThrow(ListNotFoundException::new));
        mav.addObject("mediaContainer", pageContainer);
        mav.addObject("mediaListId", mediaListId);
        mav.addObject("searchTerm", searchTerm);
        mav.addObject("searchResults", searchResults);
        return mav;
    }
}
