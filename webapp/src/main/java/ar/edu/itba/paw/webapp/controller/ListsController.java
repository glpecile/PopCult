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

    @RequestMapping("/lists")
    public ModelAndView lists(@RequestParam(value = "page", defaultValue = "0") final int page){
        final ModelAndView mav = new ModelAndView("lists");
        final List<MediaList> discoveryLists = listsService.getDiscoveryMediaLists();
        final List<ListCover> listCovers = new ArrayList<>();
        final List<ListCover> recentlyAddedCovers = new ArrayList<>();
        final List<MediaList> recentlyAdded = listsService.getLastAddedLists(page, itemsPerPage);
        generateCoverList(discoveryLists, listCovers);
        generateCoverList(recentlyAdded, recentlyAddedCovers);
        mav.addObject("covers", listCovers);
        mav.addObject("recentyAdded", recentlyAddedCovers);
        return mav;
    }

    private void generateCoverList(List<MediaList> discoveryLists, List<ListCover> listCovers) {
        List<Media> mediaList;
        for (MediaList list: discoveryLists) {
            mediaList = mediaService.getMediaListByListId(list.getMediaListId(),0,4); //request fijo de 4 para el thumbnail
            listCovers.add(new ListCover(list.getName(), list.getDescription(),
                    mediaList.get(0).getImage(),mediaList.get(1).getImage(),
                    mediaList.get(2).getImage(), mediaList.get(3).getImage()));
        }
    }

    @RequestMapping("/lists/{listId}")
    public ModelAndView listDescription(@PathVariable("listId") final int listId){
        final ModelAndView mav = new ModelAndView("listDescription");
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final List<Integer> mediaInList = listsService.getMediaIdInList(listId);
        final List<Media> mediaFromList = mediaService.getById(mediaInList);
        mav.addObject("list",mediaList);
        mav.addObject("media",mediaFromList);
        return mav;
    }

}
