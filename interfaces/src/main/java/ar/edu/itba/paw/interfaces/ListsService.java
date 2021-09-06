package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface ListsService {
    Optional<MediaList> getMediaListById(int mediaListId);

    List<MediaList> getMediaListByUserId(int userId);

    List<MediaList> getDiscoveryMediaLists();

    List<Integer> getMediaIdInList(int mediaListId);

    List<MediaList> getLastAddedLists(int page, int pageSize);

    List<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize);

}
