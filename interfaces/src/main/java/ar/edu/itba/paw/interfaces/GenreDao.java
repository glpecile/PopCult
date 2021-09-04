package ar.edu.itba.paw.interfaces;

import java.util.List;

public interface GenreDao {
    List<String> getGenreByMediaId(int mediaId);

    void loadGenres();

}
