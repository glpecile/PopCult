package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreDao genreDao;

    @Override
    public List<String> getGenreByMediaId(int mediaId) {
        return genreDao.getGenreByMediaId(mediaId);
    }

    @Override
    public void loadGenres() {
        genreDao.loadGenres();
    }
}
