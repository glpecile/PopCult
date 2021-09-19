package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int sort);

    Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType);

    PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize);

    Optional<Integer> getCountSearchMediaByTitle(String title);

    PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort);

}
