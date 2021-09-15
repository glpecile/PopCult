package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import java.util.ArrayList;
import java.util.List;

public class ListCoverImpl {
    private static final int coverMediaAmount = 4;

    public static List<ListCover> getListCover(List<MediaList> discoveryLists, ListsService listsService, MediaService mediaService) {
        List<ListCover> listCovers = new ArrayList<>();
        List<Media> mediaList;
        List<Integer> id;
        ListCover cover;
        int size;
        for (MediaList list : discoveryLists) {
            id = listsService.getNMediaIdInList(list.getMediaListId(), coverMediaAmount);
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
