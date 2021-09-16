package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<String> getGenreByMediaId(int mediaId);

    PageContainer<Integer> getMediaByGenre(int genreId, int page, int pageSize);

    Optional<Integer> getMediaCountByGenre(int genreId);

    void loadGenres();

}
