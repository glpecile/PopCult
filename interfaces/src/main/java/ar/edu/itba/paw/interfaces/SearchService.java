package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    List<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType);

    Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType);

    List<Media> searchMediaByTitle(String title, int page, int pageSize);

    Optional<Integer> getCountSearchMediaByTitle(String title);

    List<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort);
}
