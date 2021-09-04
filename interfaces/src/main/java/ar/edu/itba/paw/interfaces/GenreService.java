package ar.edu.itba.paw.interfaces;

import java.util.List;

public interface GenreService {
    List<String> getGenreByMediaId(int mediaId);

    void loadGenres();

}
