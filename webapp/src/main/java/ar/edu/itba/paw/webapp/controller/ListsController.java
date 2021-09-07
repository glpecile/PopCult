package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
        final List<MediaList> discoveryLists = listsService.getDiscoveryMediaLists(discoveryListsAmount);
        final List<MediaList> recentlyAdded = listsService.getNLastAddedList(lastAddedAmount);
        final List<MediaList> allLists = listsService.getAllLists(page - 1, itemsPerPage);
        final List<ListCover> discoveryCovers = new ArrayList<>();
        final List<ListCover> recentlyAddedCovers = new ArrayList<>();
        final List<ListCover> allListsCovers = new ArrayList<>();
        final Integer allListsCount = listsService.getListCount().orElse(0);
        generateCoverList(discoveryLists, discoveryCovers);
        generateCoverList(recentlyAdded, recentlyAddedCovers);
        generateCoverList(allLists, allListsCovers);
        mav.addObject("discovery", discoveryCovers);
        mav.addObject("recentlyAdded", recentlyAddedCovers);
        mav.addObject("allLists", allListsCovers);
        mav.addObject("allListsPages", (int)Math.ceil((double)allListsCount / itemsPerPage));
        mav.addObject("currentPage", page);
        return mav;
    }

    private void generateCoverList(List<MediaList> MediaListLists, List<ListCover> covers) {
        getListCover(MediaListLists, covers, listsService, mediaService);
    }

    @RequestMapping("/lists/{listId}")
    public ModelAndView listDescription(@PathVariable("listId") final int listId) {
        final ModelAndView mav = new ModelAndView("listDescription");
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final List<Integer> mediaInList = listsService.getMediaIdInList(listId);
        final List<Media> mediaFromList = mediaService.getById(mediaInList);
        mav.addObject("list", mediaList);
        mav.addObject("media", mediaFromList);
        return mav;
    }

    static void getListCover(List<MediaList> discoveryLists, List<ListCover> listCovers, ListsService listsService, MediaService mediaService) {
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
//        for (MediaList list : discoveryLists) {
//            id = listsService.getMediaIdInList(list.getMediaListId());
//            mediaList = mediaService.getById(id);
//            listCovers.add(new ListCover(list.getMediaListId(), list.getName(), list.getDescription(),
//                    mediaList.get(0).getImage(), mediaList.get(1).getImage(),
//                    mediaList.get(2).getImage(), mediaList.get(3).getImage(), mediaList.size()));
//        }
    }
}
