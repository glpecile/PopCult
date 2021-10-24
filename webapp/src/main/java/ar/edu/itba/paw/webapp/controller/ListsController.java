package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.MediaNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.ListForm;
import ar.edu.itba.paw.webapp.form.ListMediaForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;


@Controller
public class ListsController {
    @Autowired
    private UserService userService;
    @Autowired
    private ListsService listsService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CollaborativeListService collaborativeListService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListsController.class);

    private static final int itemsPerPage = 4;
    private static final int scrollerAmount = 6;
    private static final int listsPerPage = 8;
    private static final int defaultValue = 1;
    private static final int searchAmount = 12;
    private static final int collaboratorsAmount = 20;
    private static final int commentsAmount = 12;

    @RequestMapping("/lists")
    public ModelAndView lists(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("lists");
        final PageContainer<MediaList> allLists = listsService.getAllLists(page - 1, listsPerPage);
        final List<ListCover> mostLikedLists = generateCoverList(favoriteService.getMostLikedLists(defaultValue - 1, scrollerAmount).getElements());
        final List<ListCover> allListsCovers = generateCoverList(allLists.getElements());
        mav.addObject("mostLikedLists", mostLikedLists);
        mav.addObject("allLists", allListsCovers);
        mav.addObject("allListContainer", allLists);
        return mav;
    }

    private List<ListCover> generateCoverList(List<MediaList> MediaListLists) {
        return getListCover(MediaListLists, listsService);
    }

    @RequestMapping(value = "/lists/{listId}", method = {RequestMethod.GET})
    public ModelAndView listDescription(@PathVariable("listId") final int listId, @ModelAttribute("commentForm") CommentForm commentForm) {
        LOGGER.info("List {} accesed.", listId);
        final ModelAndView mav = new ModelAndView("listDescription");
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User u = mediaList.getUser();
        final List<Media> mediaFromList = listsService.getMediaIdInList(mediaList);
        final PageContainer<Comment> listCommentsContainer = commentService.getListComments(listId, defaultValue - 1, itemsPerPage);
        final PageContainer<Request> collaborators = collaborativeListService.getListCollaborators(mediaList, defaultValue - 1, collaboratorsAmount);
        final PageContainer<MediaList> forks = listsService.getListForks(mediaList, defaultValue - 1, itemsPerPage);
        mav.addObject("forks", forks);
        mav.addObject("collaborators", collaborators);
        mav.addObject("list", mediaList);
        mav.addObject("media", mediaFromList);
        mav.addObject("user", u);
        mav.addObject("listCommentsContainer", listCommentsContainer);
        listsService.getForkedFrom(mediaList).ifPresent(forkedFrom -> mav.addObject("forkedFrom", forkedFrom));
        userService.getCurrentUser().ifPresent(user -> {
            mav.addObject("currentUser", user);
            mav.addObject("isFavoriteList", favoriteService.isFavoriteList(mediaList, user));
            mav.addObject("canEdit", listsService.canEditList(user, mediaList));
        });
        return mav;
    }

    @RequestMapping("/lists/{listId}/comments")
    public ModelAndView listComments(@PathVariable("listId") final int listId, @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("List {} comments accesed.", listId);
        final ModelAndView mav = new ModelAndView("listCommentDetails");
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final PageContainer<Comment> listCommentsContainer = commentService.getListComments(listId, page - 1, commentsAmount);
        mav.addObject("list", mediaList);
        mav.addObject("listCommentsContainer", listCommentsContainer);
        userService.getCurrentUser().ifPresent(user -> mav.addObject("currentUser", user));
        return mav;
    }

    @RequestMapping(value = {"/lists/{listId}"}, method = {RequestMethod.POST}, params = "comment")
    public ModelAndView addComment(@PathVariable("listId") final int listId, @Valid @ModelAttribute("searchForm") final CommentForm form, final BindingResult errors) {
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if (errors.hasErrors()) {
            LOGGER.warn("List {} adding comment form has errors.", listId);
            return listDescription(listId, form);
        }
        commentService.addCommentToList(user.getUserId(), listId, form.getBody());
        LOGGER.info("List {} comment added.", listId);
        return new ModelAndView("redirect:/lists/" + listId);
    }

    @RequestMapping(value = "/lists/{listId}/deleteComment/{commentId}", method = {RequestMethod.DELETE, RequestMethod.POST}, params = "currentURL")
    public ModelAndView deleteComment(@PathVariable("listId") final int listId, @PathVariable("commentId") int commentId, @RequestParam("currentURL") final String currentURL) {
        LOGGER.debug("Trying to delete comment {} from list {}", commentId, listId);
        commentService.deleteCommentFromList(commentId);
        LOGGER.info("Comment {} deleted from list {}", commentId, listId);
        return new ModelAndView("redirect:/lists/" + listId + currentURL);
    }

    @RequestMapping(value = "/lists/{listId}/sendRequest", method = {RequestMethod.POST})
    public ModelAndView sendRequestToCollab(@PathVariable("listId") final int listId) {

        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        MediaList list = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        LOGGER.debug("User: {} requesting collaboration level for list {}", user.getUserId(), listId);
        collaborativeListService.makeNewRequest(list, user);
        LOGGER.info("User: {} send a request to collaborate to list {}", user.getUserId(), listId);
        return new ModelAndView("redirect:/lists/" + listId);
    }

    @RequestMapping(value = "/lists/{listId}/cancelCollab", method = {RequestMethod.POST})
    public ModelAndView cancelCollabPermissions(@PathVariable("listId") final int listId, @RequestParam("collabId") final int collabId, @RequestParam("returnURL") final String returnURL) {
        LOGGER.debug("Cancelling colaborration permission for list {}", listId);
        Request collab = collaborativeListService.getById(collabId).orElseThrow(RuntimeException::new); //TODO CUSTOM EXCEPTION
        collaborativeListService.deleteCollaborator(collab);
        LOGGER.info("Cancelling colaborration permission for list {} completed.", listId);
        return new ModelAndView("redirect:" + returnURL);
    }

    //CREATE A NEW LIST - PART 1
    @RequestMapping(value = "/lists/new", method = {RequestMethod.GET})
    public ModelAndView createListForm(@ModelAttribute("createListForm") final ListForm form) {
        return new ModelAndView("createListForm");
    }

    @RequestMapping(value = "/lists/new", method = {RequestMethod.POST})
    public ModelAndView postListForm(@Valid @ModelAttribute("createListForm") final ListForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            LOGGER.warn("Create a new list form has errors.");
            return createListForm(form);
        }
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        final MediaList mediaList = listsService.createMediaList(user, form.getListTitle(), form.getDescription(), form.isVisible(), form.isCollaborative());
        LOGGER.info("New list created, id = {}", mediaList.getMediaListId());
        return new ModelAndView("redirect:/lists/edit/" + mediaList.getMediaListId() + "/manageMedia");
    }

    // MANAGE MEDIA IN LIST
    @RequestMapping(value = "/lists/edit/{listId}/manageMedia", method = {RequestMethod.GET})
    public ModelAndView manageMediaFromList(@PathVariable("listId") Integer mediaListId, @RequestParam(value = "page", defaultValue = "1") final int page, @ModelAttribute("editListDetails") final ListForm form, @ModelAttribute("mediaForm") ListMediaForm mediaForm) {
        final ModelAndView mav = new ModelAndView("manageMediaFromList");
        return addMediaObjects(mediaListId, mediaForm, page, mav);
    }

    @RequestMapping(value = "/lists/edit/{listId}/deleteMedia", method = {RequestMethod.DELETE, RequestMethod.POST})
    public ModelAndView deleteFromList(@PathVariable("listId") Integer mediaListId, @RequestParam("mediaId") Integer mediaId) {
        MediaList mediaList = listsService.getMediaListById(mediaListId).orElseThrow(ListNotFoundException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        listsService.deleteMediaFromList(mediaList, media);
        return new ModelAndView("redirect:/lists/edit/" + mediaListId + "/manageMedia");
    }

    @RequestMapping(value = "/lists/edit/{listId}/search", method = {RequestMethod.GET}, params = "search")
    public ModelAndView searchMediaToAddToList(@PathVariable("listId") Integer mediaListId,
                                               @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                               final BindingResult errors,
                                               @RequestParam(value = "sort", defaultValue = "title") final String sortType,
                                               @ModelAttribute("editListDetails") final ListForm form, @ModelAttribute("mediaForm") ListMediaForm mediaForm) {

        if (errors.hasErrors()) {
            LOGGER.warn("Search form has errors for list {}", mediaListId);
            return manageMediaFromList(mediaListId, defaultValue, form, mediaForm);
        }
        LOGGER.info("Searching for term: {}", searchForm.getTerm());
        final MediaList mediaList = listsService.getMediaListById(mediaListId).orElseThrow(ListNotFoundException::new);
        final List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.SERIE);
        mediaTypes.add(MediaType.FILMS);
        final List<Media> searchResults = searchService.searchMediaByTitleNotInList(mediaList, searchForm.getTerm(), defaultValue - 1, searchAmount, mediaTypes, SortType.valueOf(sortType.toUpperCase())).getElements();
        searchResults.addAll(searchService.searchMediaByTitleNotInList(mediaList, searchForm.getTerm(), defaultValue - 1, searchAmount, mediaTypes, SortType.valueOf(sortType.toUpperCase())).getElements());
        LOGGER.info("Search process completed.");
        return manageMediaFromList(mediaListId, defaultValue, form, mediaForm).addObject("searchTerm", searchForm.getTerm()).addObject("searchResults", mediaForm.generateMediaMap(searchResults));

    }

    @RequestMapping(value = "/lists/edit/{listId}/addMedia", method = {RequestMethod.POST}, params = "add")
    public ModelAndView insertToList(@PathVariable("listId") Integer mediaListId, @ModelAttribute("editListDetails") final ListForm form, @Valid @ModelAttribute("mediaForm") ListMediaForm mediaForm, final BindingResult errors) {
        LOGGER.debug("Trying to add media to list {}", mediaListId);
        if (errors.hasErrors()) {
            LOGGER.warn("Media form has errors for list {}", mediaListId);
            return manageMediaFromList(mediaListId, defaultValue, form, mediaForm);
        }
        MediaList mediaList = listsService.getMediaListById(mediaListId).orElseThrow(ListNotFoundException::new);
        List<Media> media = mediaService.getById(mediaForm.getMedia());
        try {
            listsService.addToMediaList(mediaList, media);
        } catch (MediaAlreadyInListException e) {
            return manageMediaFromList(mediaListId, defaultValue, form, mediaForm).addObject("alreadyInList", true);
        }
        LOGGER.info("Media {} added to list {}", mediaForm.getMedia(), mediaListId);
        return new ModelAndView("redirect:/lists/edit/" + mediaListId + "/manageMedia");
    }

    @RequestMapping(value = "/lists/edit/{listId}/delete", method = {RequestMethod.DELETE, RequestMethod.POST}, params = "delete")
    public ModelAndView deleteList(@PathVariable("listId") final int listId) {
        LOGGER.debug("Trying to delete list {}", listId);
        MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        listsService.deleteList(mediaList);
        LOGGER.info("List {} deleted.", listId);
        return new ModelAndView("redirect:/lists");
    }

    @RequestMapping(value = "/lists/edit/{listId}/update", method = {RequestMethod.POST}, params = "save")
    public ModelAndView submitList(@PathVariable("listId") final int listId, @Valid @ModelAttribute("editListDetails") final ListForm form, final BindingResult errors, @ModelAttribute("mediaForm") ListMediaForm mediaForm) {
        LOGGER.debug("Trying to update list {}", listId);
        if (errors.hasErrors()) {
            LOGGER.warn("List {} form for update has errors", listId);
            return manageMediaFromList(listId, defaultValue, form, mediaForm).addObject("editDetailsErrors", errors.hasErrors());
        }
        MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        listsService.updateList(mediaList, form.getListTitle(), form.getDescription(), form.isVisible(), form.isCollaborative());
        LOGGER.info("List {} updated.", listId);
        return new ModelAndView("redirect:/lists/edit/" + listId + "/manageMedia");
    }
    //END EDIT LIST

    @RequestMapping(value = "/lists/{listId}", method = {RequestMethod.POST}, params = "fork")
    public ModelAndView createListCopy(@PathVariable("listId") final int listId) {
        LOGGER.debug("Forking list {}", listId);
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final MediaList newList = listsService.createMediaListCopy(user, mediaList);
        LOGGER.info("List {} forked.", listId);
        return new ModelAndView("redirect:/lists/" + newList.getMediaListId());
    }

    @RequestMapping(value = "/lists/{listId}", method = {RequestMethod.POST}, params = "addFav")
    public ModelAndView addListToFav(@PathVariable("listId") final int listId) {
        LOGGER.debug("Trying to add list {} to favorties.", listId);
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        MediaList list = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        favoriteService.addListToFav(list, user);
        LOGGER.info("List {} added to favorites.", listId);
        return new ModelAndView("redirect:/lists/" + listId);
    }

    @RequestMapping(value = "/lists/{listId}", method = {RequestMethod.POST}, params = "deleteFav")
    public ModelAndView deleteListFromFav(@PathVariable("listId") final int listId) {
        LOGGER.debug("Trying to delete list {} from favorites.", listId);
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        MediaList list = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        favoriteService.deleteListFromFav(list, user);
        LOGGER.info("List {} deleted from favorites.", listId);
        return new ModelAndView("redirect:/lists/" + listId);
    }

    @RequestMapping(value = "/lists/{listId}/collaborators")
    public ModelAndView manageListCollaborators(@PathVariable("listId") final int listId, @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access list {} collaborators", listId);
        final ModelAndView mav = new ModelAndView("manageCollaboratorsFromList");
        MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        mav.addObject("list", mediaList);
        mav.addObject("collaboratorsContainer", collaborativeListService.getListCollaborators(mediaList, page - 1, collaboratorsAmount));
        LOGGER.info("List collaborators from {} accessed.", listId);
        return mav;
    }

    private ModelAndView addMediaObjects(@PathVariable("listId") Integer mediaListId, @ModelAttribute("mediaForm") ListMediaForm mediaForm, @RequestParam(value = "page", defaultValue = "1") final int page, ModelAndView mav) {
        MediaList mediaList = listsService.getMediaListById(mediaListId).orElseThrow(ListNotFoundException::new);
        PageContainer<Media> pageContainer = listsService.getMediaIdInList(mediaList, page - 1, itemsPerPage);
        mav.addObject("list", listsService.getMediaListById(mediaListId).orElseThrow(ListNotFoundException::new));
        mav.addObject("mediaContainer", pageContainer);
        mav.addObject("mediaListId", mediaListId);
        mav.addObject("collaboratorsContainer", collaborativeListService.getListCollaborators(mediaList, defaultValue - 1, collaboratorsAmount));
        mav.addObject("currentUser", userService.getCurrentUser().orElseThrow(UserNotFoundException::new));
        return mav;
    }
}
