package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;

public interface GenreDao {
    PageContainer<Media> getMediaByGenre(Genre genre, int page, int pageSize);

    List<MediaList> getListsContainingGenre(Genre genre, int pageSize, int minMatches);

}
