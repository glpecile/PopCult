package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SearchDAO;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDAO searchDAO;

    @Override
    public List<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType) {
        return searchDAO.searchMediaByTitle(title,page,pageSize, mediaType);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType) {
        return searchDAO.getCountSearchMediaByTitle(title, mediaType);
    }

    @Override
    public List<Media> searchMediaByTitle(String title, int page, int pageSize) {
        return searchDAO.searchMediaByTitle(title,page,pageSize);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title) {
        return searchDAO.getCountSearchMediaByTitle(title);
    }

    @Override
    public List<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort) {
        return searchDAO.searchMediaByTitle(title,page,pageSize,mediaType);
    }

}
