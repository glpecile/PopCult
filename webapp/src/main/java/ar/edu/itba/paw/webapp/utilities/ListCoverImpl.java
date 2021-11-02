package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import java.util.ArrayList;
import java.util.List;

public class ListCoverImpl {

    private ListCoverImpl() {
        throw new AssertionError();
    }

    private static final int coverMoviesAmount = 4;

    public static List<ListCover> getListCover(List<MediaList> discoveryLists, ListsService listsService) {
        List<ListCover> listCovers = new ArrayList<>();
        List<Media> mediaList;
        PageContainer<Integer> id;
        ListCover cover;
        int size;
        for (MediaList list : discoveryLists) {
            mediaList = listsService.getMediaIdInList(list, 0, coverMoviesAmount).getElements();
            size = mediaList.size();
            cover = new ListCover(list.getMediaListId(), list.getListName(), list.getDescription());
            if (size > 0) cover.setImage1(mediaList.get(0).getImage());
            if (size > 1) cover.setImage2(mediaList.get(1).getImage());
            if (size > 2) cover.setImage3(mediaList.get(2).getImage());
            if (size > 3) cover.setImage4(mediaList.get(3).getImage());
            listCovers.add(cover);
        }
        return listCovers;
    }
}
