package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreDao genreDao;

    @Override
    public List<String> getGenreByMediaId(int mediaId) {
        return genreDao.getGenreByMediaId(mediaId);
    }

    @Override
    public List<Integer> getMediaByGenre(int genreId, int page, int pageSize) {
        return genreDao.getMediaByGenre(genreId, page, pageSize);
    }

    @Override
    public Optional<Integer> getMediaCountByGenre(int genreId) {
        return genreDao.getMediaCountByGenre(genreId);
    }

    @Override
    public void loadGenres() {
        genreDao.loadGenres();
    }
}
