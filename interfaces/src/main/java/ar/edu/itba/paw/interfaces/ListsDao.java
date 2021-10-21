package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface ListsDao {
    Optional<MediaList> getMediaListById(int mediaListId);

    PageContainer<MediaList> getAllLists(int page, int pageSize);

    PageContainer<MediaList> getMediaListByUser(User user, int page, int pageSize);

    PageContainer<MediaList> getPublicMediaListByUser(User user, int page, int pageSize);

    List<Media> getMediaIdInList(MediaList mediaList);//TODO BORRAR

    PageContainer<Media> getMediaIdInList(MediaList mediaList, int page, int pageSize);

    PageContainer<MediaList> getLastAddedLists(int page, int pageSize); //TODO optional probar

    PageContainer<MediaList> getListsIncludingMedia(Media media, int page, int pageSize);

    List<MediaList> getListsContainingGenre(Genre genre, int pageSize, int minMatches); //TODO paginar

    MediaList createMediaList(User user, String title, String description, boolean visibility, boolean collaborative);

    void addToMediaList(int mediaListId, int mediaId) throws MediaAlreadyInListException;

    void addToMediaList(int mediaListId, List<Integer> mediaIdList) throws MediaAlreadyInListException;

    void deleteMediaFromList(int mediaListId, int mediaId);

    void deleteList(int mediaListId);

    void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative);

    MediaList createMediaListCopy(int userId, int toCopyListId);

    Optional<User> getListOwner(int listId);

    boolean canEditList(int userId, int listId);

    PageContainer<MediaList> getUserEditableLists(int userId, int page, int pageSize);

    PageContainer<MediaList> getListForks(int listId, int page, int pageSize);

    Optional<MediaList> getForkedFrom(int listId);
}
