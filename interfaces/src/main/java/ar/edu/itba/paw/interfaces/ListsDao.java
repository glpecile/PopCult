package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface ListsDao {
    Optional<MediaList> getMediaListById(int mediaListId);

    List<MediaList> getMediaListById(List<Integer> mediaListId);

    List<MediaList> getAllLists(int page, int pageSize);

    List<MediaList> getMediaListByUserId(int userId);//TODO BORRAR

    List<MediaList> getMediaListByUserId(int userId, int page, int pageSize);

    List<MediaList> getDiscoveryMediaLists(int pageSize);

    List<Integer> getMediaIdInList(int mediaListId);//TODO BORRAR

    List<Integer> getMediaIdInList(int mediaListId, int page, int pageSize);

    List<MediaList> getLastAddedLists(int page, int pageSize); //TODO optional probar

    List<MediaList> getNLastAddedList(int amount);//TODO BORRAR. reemplaza la de arriba

    List<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize);

    Optional<Integer> getListCount();

    Optional<Integer> getListCountFromUserId(int userId);

    Optional<Integer> getListCountFromMedia(int mediaId);

    List<MediaList> getListsContainingGenre(int genreId, int pageSize, int minMatches); //TODO paginar

    MediaList createMediaList(int userId, String title, String description, boolean visibility, boolean collaborative);

    void addToMediaList(int mediaListId, int mediaId);

    void addToMediaList(int mediaListId, List<Integer> mediaIdList);

    void deleteMediaFromList(int mediaListId, int mediaId);

    void deleteList(int mediaListId);

    void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative);

    MediaList createMediaListCopy(int userId, int toCopy);
}
