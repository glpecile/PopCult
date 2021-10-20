package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    PageContainer<Media> getMediaByGenre(Genre genre, int page, int pageSize);
}
