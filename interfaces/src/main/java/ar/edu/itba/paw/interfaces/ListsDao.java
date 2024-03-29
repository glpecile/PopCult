package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ListsDao {
    Optional<MediaList> getMediaListById(int mediaListId);

    PageContainer<MediaList> getMediaListByUser(User user, int page, int pageSize);

    PageContainer<MediaList> getPublicMediaListByUser(User user, int page, int pageSize);

    PageContainer<Media> getMediaInList(MediaList mediaList, int page, int pageSize);

    PageContainer<MediaList> getMediaListByFilters(int page, int pageSize, SortType sort, List<Genre> genre, int minMatches, LocalDateTime fromDate, LocalDateTime toDate, String term);

    PageContainer<MediaList> getListsIncludingMedia(Media media, int page, int pageSize);

    MediaList createMediaList(User user, String title, String description, boolean visibility, boolean collaborative);

    void addToMediaList(MediaList mediaList, Media media);

    boolean mediaAlreadyInList(MediaList mediaList, Media media);

    void addToMediaList(MediaList mediaList, List<Media> media);

    void deleteMediaFromList(MediaList mediaList, Media media);

    void deleteMediaFromList(MediaList mediaList, List<Media> media);

    void deleteList(MediaList mediaList);

    MediaList createMediaListCopy(User user, MediaList toCopy);

    boolean canEditList(User user, MediaList mediaList);

    PageContainer<MediaList> getUserEditableLists(User user, int page, int pageSize);

    PageContainer<MediaList> getListForks(MediaList mediaList, int page, int pageSize);

}
