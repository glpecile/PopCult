package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        final List<MediaList> discoveryLists = listsService.getDiscoveryMediaLists();
        System.out.println("discovery size: "+discoveryLists.size());
        for (MediaList media: discoveryLists) {
            System.out.println(media.getName());
        }
        List<Media> media = mediaService.getMediaList();
        mav.addObject("discoveryLists", media);
        mav.addObject(media.get(0));
        System.out.println(media.get(0).getTitle());
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
