package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreDao genreDao;

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaByGenre(Genre genre, int page, int pageSize) {
        return genreDao.getMediaByGenre(genre, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MediaList> getListsContainingGenre(Genre genre, int pageSize, int minMatches) {
        return null;
    }
}
