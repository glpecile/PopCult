package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreDao genreDao;

    @Transactional(readOnly = true)
    @Override
    public List<String> getGenreByMediaId(int mediaId) {
        return genreDao.getGenreByMediaId(mediaId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaByGenre(int genreId, int page, int pageSize) {
        return genreDao.getMediaByGenre(genreId, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Integer> getMediaCountByGenre(int genreId) {
        return genreDao.getMediaCountByGenre(genreId);
    }

    @Override
    public void loadGenres() {
        genreDao.loadGenres();
    }
}
