package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface SearchDAO {
    List<Media> searchMediaByTitle(String title, int page, int pageSize, int sort);

    Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType);

    List<Media> searchMediaByTitle(String title, int page, int pageSize);

    Optional<Integer> getCountSearchMediaByTitle(String title);

    List<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort);


}
