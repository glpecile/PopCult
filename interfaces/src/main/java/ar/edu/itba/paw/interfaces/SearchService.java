package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;

import java.text.ParseException;
import java.util.List;

public interface SearchService {

    PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, List<MediaType> mediaType, SortType sort, List<Genre> genre, String fromDate, String toDate) throws ParseException;

    PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, SortType sort, List<Genre> genre, int minMatches);

    PageContainer<Media> searchMediaByTitleNotInList(MediaList mediaList, String title, int page, int pageSize, List<MediaType> mediaType, SortType sort);

    PageContainer<User> searchUsersToCollabNotInList(String username, MediaList mediaList, int pageSize, int page);

}
