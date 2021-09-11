package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface ListsService {
    Optional<MediaList> getMediaListById(int mediaListId);

    List<MediaList> getAllLists(int page, int pageSize);

    List<MediaList> getMediaListByUserId(int userId);

    List<MediaList> getDiscoveryMediaLists(int pageSize);

    List<Integer> getMediaIdInList(int mediaListId);

    List<MediaList> getLastAddedLists(int page, int pageSize);

    List<MediaList> getNLastAddedList(int amount);

    List<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize);

    Optional<Integer> getListCount();

    Optional<Integer> getListCountFromMedia(int mediaId);

    List<MediaList> getListsContainingGenre(int genreId, int pageSize, int minMatches);

    MediaList createMediaList(String title, String description, String image, boolean visibility, boolean collaborative);

}
