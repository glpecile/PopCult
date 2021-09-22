package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface ListsDao {
    Optional<MediaList> getMediaListById(int mediaListId);

    List<MediaList> getMediaListById(List<Integer> mediaListId);

    PageContainer<MediaList> getAllLists(int page, int pageSize);

    List<MediaList> getMediaListByUserId(int userId);//TODO BORRAR -> no, se usa para el dropdown para agregar una media a una lista desde media descr

    PageContainer<MediaList> getMediaListByUserId(int userId, int page, int pageSize);

    PageContainer<MediaList> getPublicMediaListByUserId(int userId, int page, int pageSize);

    List<MediaList> getDiscoveryMediaLists(int pageSize);

//    @Deprecated
//    List<Integer> getMediaIdInListIds(int mediaListId);//TODO BORRAR

    List<Media> getMediaIdInList(int mediaListId);//TODO BORRAR

//    @Deprecated
//    PageContainer<Integer> getMediaIdInListIds(int mediaListId, int page, int pageSize);

    PageContainer<Media> getMediaIdInList(int mediaListId, int page, int pageSize);

    PageContainer<MediaList> getLastAddedLists(int page, int pageSize); //TODO optional probar

    List<MediaList> getNLastAddedList(int amount);//TODO BORRAR. reemplaza la de arriba

    PageContainer<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize);

//    Optional<Integer> getListCount();
//
//    Optional<Integer> getListCountFromUserId(int userId);
//
//    Optional<Integer> getListCountFromMedia(int mediaId);

    List<MediaList> getListsContainingGenre(int genreId, int pageSize, int minMatches); //TODO paginar

    MediaList createMediaList(int userId, String title, String description, boolean visibility, boolean collaborative);

    void addToMediaList(int mediaListId, int mediaId);

    void addToMediaList(int mediaListId, List<Integer> mediaIdList);

    void deleteMediaFromList(int mediaListId, int mediaId);

    void deleteList(int mediaListId);

    void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative);

    Optional<MediaList> createMediaListCopy(int userId, int toCopyListId);

    Optional<User> getListOwner(int listId);

    PageContainer<MediaList> getMostLikedLists(int page, int pageSize);

}
