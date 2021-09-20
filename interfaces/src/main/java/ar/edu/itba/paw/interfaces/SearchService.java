package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import java.util.Optional;

public interface SearchService {
    PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int sort);

    Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType);

    PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize);

    Optional<Integer> getCountSearchMediaByTitle(String title);

    PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort);

    PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, int sort);

    PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort, int genre);

    PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, int sort, int genre, int minMatches);

    PageContainer<Media> searchMediaByTitleNotInList(int listId, String title, int page, int pageSize, int mediaType, int sort);


}
