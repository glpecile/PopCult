package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import ar.edu.itba.paw.webapp.form.ListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ListsController {
    @Autowired
    private UserService userService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private ListsService listsService;

    private static final int itemsPerPage = 4;
    private static final int discoveryListsAmount = 4;
    private static final int lastAddedAmount = 4;

    @RequestMapping("/lists")
    public ModelAndView lists(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("lists");
        final List<MediaList> allLists = listsService.getAllLists(page - 1, itemsPerPage);
        final List<ListCover> discoveryCovers = generateCoverList(listsService.getDiscoveryMediaLists(discoveryListsAmount));
        final List<ListCover> recentlyAddedCovers = generateCoverList(listsService.getNLastAddedList(lastAddedAmount));
        final List<ListCover> allListsCovers = generateCoverList(allLists);
        final Integer allListsCount = listsService.getListCount().orElse(0);
        mav.addObject("discovery", discoveryCovers);
        mav.addObject("recentlyAdded", recentlyAddedCovers);
        mav.addObject("allLists", allListsCovers);
        mav.addObject("allListsPages", (int) Math.ceil((double) allListsCount / itemsPerPage));
        mav.addObject("currentPage", page);
        return mav;
    }

    private List<ListCover> generateCoverList(List<MediaList> MediaListLists) {
        return getListCover(MediaListLists, listsService, mediaService);
    }

    @RequestMapping("/lists/{listId}")
    public ModelAndView listDescription(@PathVariable("listId") final int listId) {
        final ModelAndView mav = new ModelAndView("listDescription");
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final List<Integer> mediaInList = listsService.getMediaIdInList(listId);
        final List<Media> mediaFromList = mediaService.getById(mediaInList);
        final User currentUser = new User(1, "", "", ""); //esto despues se reemplaza por el context del current user
        mav.addObject("list", mediaList);
        mav.addObject("media", mediaFromList);
        mav.addObject("currentUser", currentUser);
        return mav;
    }

    @RequestMapping(value = "/createList", method = {RequestMethod.GET})
    public ModelAndView createListForm(@ModelAttribute("createListForm") final ListForm form) {
        return new ModelAndView("createListForm");
    }

    @RequestMapping(value = "/createList", method = {RequestMethod.POST})
    public ModelAndView postListForm(@Valid @ModelAttribute("createListForm") final ListForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return createListForm(form);
        final MediaList mediaList = listsService.createMediaList(1, form.getListTitle(), form.getDescription(), form.isVisible(), form.isCollaborative());
        return new ModelAndView("redirect:/lists/" + mediaList.getMediaListId());
    }

    @RequestMapping("/editList/{listId}")
    public ModelAndView editList(@PathVariable("listId") final int listId) {
        final ModelAndView mav = new ModelAndView("editList");
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final List<Integer> mediaInList = listsService.getMediaIdInList(listId);
        final List<Media> mediaFromList = mediaService.getById(mediaInList);
        mav.addObject("list", mediaList);
        mav.addObject("media", mediaFromList);
        return mav;
    }

    @RequestMapping(value = "/editList/{listId}", method = {RequestMethod.POST}, params = "mediaId")
    public ModelAndView deleteMediaFromList(@PathVariable("listId") final int listId, @RequestParam("mediaId") final int mediaId) {
        System.out.println(mediaId);
        System.out.println(listId);
        listsService.deleteMediaFromList(listId, mediaId);
        return new ModelAndView("redirect:/editList/" + listId);
    }

    static List<ListCover> getListCover(List<MediaList> discoveryLists, ListsService listsService, MediaService mediaService) {
        List<ListCover> listCovers = new ArrayList<>();
        List<Media> mediaList;
        List<Integer> id;
        ListCover cover;
        int size;
        for (MediaList list : discoveryLists) {
            id = listsService.getMediaIdInList(list.getMediaListId());
            mediaList = mediaService.getById(id);
            size = mediaList.size();
            cover = new ListCover(list.getMediaListId(), list.getName(), list.getDescription());
            if (size > 0) cover.setImage1(mediaList.get(0).getImage());
            if (size > 1) cover.setImage2(mediaList.get(1).getImage());
            if (size > 2) cover.setImage3(mediaList.get(2).getImage());
            if (size > 3) cover.setImage4(mediaList.get(3).getImage());
            listCovers.add(cover);
        }
        return listCovers;
    }
}
