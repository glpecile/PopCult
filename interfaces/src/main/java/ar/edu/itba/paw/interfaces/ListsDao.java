package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;
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

    PageContainer<MediaList> getMediaListByFilters(int page, int pageSize, SortType sort, List<Genre> genre, int minMatches);

    PageContainer<MediaList> getListsIncludingMedia(Media media, int page, int pageSize);

    MediaList createMediaList(User user, String title, String description, boolean visibility, boolean collaborative);

    void addToMediaList(MediaList mediaList, Media media) throws MediaAlreadyInListException;

    void addToMediaList(MediaList mediaList, List<Media> medias) throws MediaAlreadyInListException;

    void deleteMediaFromList(MediaList mediaList, Media media);

    void deleteList(MediaList mediaList);

    MediaList createMediaListCopy(User user, MediaList toCopy);

    boolean canEditList(User user, MediaList mediaList);

    PageContainer<MediaList> getUserEditableLists(User user, int page, int pageSize);

    PageContainer<MediaList> getListForks(MediaList mediaList, int page, int pageSize);

}
