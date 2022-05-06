package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;

public interface GenreService {

    PageContainer<Media> getMediaByGenre(Genre genre, int page, int pageSize);

    PageContainer<MediaList> getListsContainingGenre(Genre genre, int page, int pageSize, int minMatches);

    List<Genre> getAllGenre ();

}
