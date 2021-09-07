package ar.edu.itba.paw.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<String> getGenreByMediaId(int mediaId);

    List<Integer> getMediaByGenre(int genreId, int page, int pageSize);

    Optional<Integer> getMediaCountByGenre(int genreId);

    void loadGenres();

}
