package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface ListsService {
    Optional<MediaList> getMediaListById(int mediaListId);

    List<MediaList> getMediaListById(List<Integer> mediaListId);

    PageContainer<MediaList> getAllLists(int page, int pageSize);

    List<MediaList> getMediaListByUserId(int userId);

    PageContainer<MediaList> getMediaListByUserId(int userId, int page, int pageSize);

    List<MediaList> getDiscoveryMediaLists(int pageSize);
    @Deprecated
    List<Integer> getMediaIdInListIds(int mediaListId);//TODO BORRAR

    List<Media> getMediaIdInList(int mediaListId);//TODO BORRAR
    @Deprecated
    PageContainer<Integer> getMediaIdInListIds(int mediaListId, int page, int pageSize);

    PageContainer<Media> getMediaIdInList(int mediaListId, int page, int pageSize);

    PageContainer<MediaList> getLastAddedLists(int page, int pageSize);

    List<MediaList> getNLastAddedList(int amount);

    PageContainer<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize);

    Optional<Integer> getListCount();

    Optional<Integer> getListCountFromUserId(int userId);

    Optional<Integer> getListCountFromMedia(int mediaId);

    List<MediaList> getListsContainingGenre(int genreId, int pageSize, int minMatches);

    MediaList createMediaList(int userId, String title, String description, boolean visibility, boolean collaborative);

    void addToMediaList(int mediaListId, int mediaId);

    void addToMediaList(int mediaListId, List<Integer> mediaIdList);

    void deleteMediaFromList(int mediaListId, int mediaId);

    void deleteList(int mediaListId);

    void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative);

    MediaList createMediaListCopy(int userId, int toCopy);
}
