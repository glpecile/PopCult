package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ListsService listsService;

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaByGenre(Genre genre, int page, int pageSize) {
        return mediaService.getMediaByFilters(Collections.emptyList(),page,pageSize, null ,Collections.singletonList(genre),null,null,null,null);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getListsContainingGenre(Genre genre, int page, int pageSize, int minMatches) {
        return listsService.getMediaListByFilters(page,pageSize,null,Collections.singletonList(genre),minMatches,null,null,null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllGenre() {
        return genreDao.getAllGenre();
    }
}
