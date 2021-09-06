package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
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
    private MediaService mediaService;
    @Autowired
    private ListsService listsService;

    private static final int itemsPerPage = 4;
    private static final int discoveryListsAmount = 4;

    @RequestMapping("/lists")
    public ModelAndView lists(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("lists");
        final List<MediaList> discoveryLists = listsService.getDiscoveryMediaLists(discoveryListsAmount);
        final List<ListCover> listCovers = new ArrayList<>();
        final List<ListCover> recentlyAddedCovers = new ArrayList<>();
        final List<MediaList> recentlyAdded = listsService.getLastAddedLists(page - 1, itemsPerPage);
        final Integer recentListsCount = listsService.getListCount().orElse(0);
        generateCoverList(discoveryLists, listCovers);
        generateCoverList(recentlyAdded, recentlyAddedCovers);
        mav.addObject("covers", listCovers);
        mav.addObject("recentlyAdded", recentlyAddedCovers);
        mav.addObject("recentListsPages", recentListsCount / itemsPerPage + 1);
        mav.addObject("currentPage", page);
        return mav;
    }

    private void generateCoverList(List<MediaList> discoveryLists, List<ListCover> listCovers) {
        getListCover(discoveryLists, listCovers, listsService, mediaService);
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
            System.out.println(cover.getName() + " " + size);
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
