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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListsController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private ListsService listsService;

    @RequestMapping("/lists")
    public ModelAndView lists(){
        final ModelAndView mav = new ModelAndView("lists");
        final List<MediaList> discoveryLists = listsService.getDiscoveryMediaLists(); //obtengo todas las listas de discovery
        final List<ListCover> listCovers = new ArrayList<>();
        for (MediaList list: discoveryLists) {
            List<Media> mediaList = mediaService.getMediaListByListId(list.getMediaListId(),0,4);
            listCovers.add(new ListCover(list.getName(), list.getDescription(),
                    mediaList.get(0).getImage(),mediaList.get(1).getImage(),
                    mediaList.get(2).getImage(), mediaList.get(3).getImage()));
        }
        mav.addObject("covers", listCovers);
        return mav;
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
